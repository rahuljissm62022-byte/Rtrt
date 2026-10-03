package com.example.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.net.URLEncoder

object ShowroomActions {

    fun dialPhone(context: Context, phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open dialer: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun openWhatsApp(context: Context, phoneNumber: String, message: String) {
        try {
            val cleanPhone = phoneNumber.replace("+", "").replace(" ", "").replace("-", "")
            val encodedMsg = URLEncoder.encode(message, "UTF-8")
            val url = "https://api.whatsapp.com/send?phone=$cleanPhone&text=$encodedMsg"
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            // Fallback to general share
            shareText(context, message, "Send via")
        }
    }

    fun shareToWhatsApp(context: Context, message: String) {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                setPackage("com.whatsapp")
                putExtra(Intent.EXTRA_TEXT, message)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            // If WhatsApp package not found, open general chooser
            shareText(context, message, "Share Offer via")
        }
    }

    fun shareText(context: Context, message: String, chooserTitle: String = "Share via") {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, message)
            }
            context.startActivity(Intent.createChooser(intent, chooserTitle))
        } catch (e: Exception) {
            Toast.makeText(context, "Could not share: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun openShowroomMap(context: Context, query: String = "Pearl Cars Nexa Sasaram Bihar") {
        try {
            val gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(query))
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(mapIntent)
            } else {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(query))
                )
                context.startActivity(browserIntent)
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Could not open map: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun saveBitmapToCache(context: Context, bitmap: Bitmap, filename: String = "nexa_poster.png"): Uri? {
        return try {
            val cachePath = File(context.cacheDir, "images")
            cachePath.mkdirs()
            val file = File(cachePath, filename)
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.flush()
            stream.close()
            FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun shareImageUri(context: Context, imageUri: Uri, captionText: String, targetPackage: String? = null) {
        try {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "image/png"
                putExtra(Intent.EXTRA_STREAM, imageUri)
                putExtra(Intent.EXTRA_TEXT, captionText)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                if (targetPackage != null) {
                    setPackage(targetPackage)
                }
            }
            if (targetPackage != null) {
                context.startActivity(intent)
            } else {
                context.startActivity(Intent.createChooser(intent, "Share Poster via"))
            }
        } catch (e: Exception) {
            // Fallback without package
            try {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "image/png"
                    putExtra(Intent.EXTRA_STREAM, imageUri)
                    putExtra(Intent.EXTRA_TEXT, captionText)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                context.startActivity(Intent.createChooser(intent, "Share Poster via"))
            } catch (err: Exception) {
                Toast.makeText(context, "Failed to share image: ${err.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun generatePosterBitmap(
        context: Context,
        festivalName: String,
        carModel: String,
        offerHeadline: String,
        startingPrice: String,
        tagline: String,
        rmName: String,
        rmPhone: String,
        showroomCity: String,
        carBitmap: Bitmap? = null
    ): Bitmap {
        val width = 1080
        val height = 1350 // standard 4:5 Instagram / WhatsApp poster size
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // Background Gradient
        val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            shader = LinearGradient(
                0f, 0f, 0f, height.toFloat(),
                intArrayOf(0xFF040A12.toInt(), 0xFF0A192F.toInt(), 0xFF0D254C.toInt(), 0xFF03070E.toInt()),
                floatArrayOf(0f, 0.35f, 0.7f, 1f),
                Shader.TileMode.CLAMP
            )
        }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), bgPaint)

        // Metallic Border
        val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 10f
            color = 0x8894A3B8.toInt()
        }
        canvas.drawRoundRect(RectF(24f, 24f, width - 24f, height - 24f), 32f, 32f, borderPaint)

        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textAlign = Paint.Align.CENTER
        }

        // Header: PEARL CARS NEXA
        textPaint.color = 0xFFFFFFFF.toInt()
        textPaint.textSize = 58f
        textPaint.isFakeBoldText = true
        canvas.drawText("PEARL CARS NEXA", width / 2f, 110f, textPaint)

        // Showroom City Subheader
        textPaint.textSize = 28f
        textPaint.color = 0xFF38BDF8.toInt()
        textPaint.isFakeBoldText = false
        canvas.drawText("SASARAM, BIHAR • OFFICIAL SHOWROOM", width / 2f, 160f, textPaint)

        // Festival Badge Box
        val badgePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = 0xFFF59E0B.toInt()
            style = Paint.Style.FILL
        }
        val badgeRect = RectF(120f, 200f, width - 120f, 280f)
        canvas.drawRoundRect(badgeRect, 20f, 20f, badgePaint)

        textPaint.color = 0xFF000000.toInt()
        textPaint.textSize = 38f
        textPaint.isFakeBoldText = true
        canvas.drawText(festivalName.uppercase(), width / 2f, 255f, textPaint)

        // Car Model Name
        textPaint.color = 0xFFFFFFFF.toInt()
        textPaint.textSize = 68f
        textPaint.isFakeBoldText = true
        canvas.drawText(carModel.uppercase(), width / 2f, 370f, textPaint)

        // Tagline
        textPaint.textSize = 30f
        textPaint.color = 0xFFCBD5E1.toInt()
        textPaint.isFakeBoldText = false
        canvas.drawText(tagline, width / 2f, 415f, textPaint)

        // Car Image Area
        if (carBitmap != null) {
            val destRect = RectF(80f, 440f, width - 80f, 850f)
            canvas.drawBitmap(carBitmap, null, destRect, null)
        } else {
            // Stylized placeholder card
            val carPlaceholderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = 0xFF14243C.toInt()
            }
            val rect = RectF(100f, 460f, width - 100f, 820f)
            canvas.drawRoundRect(rect, 24f, 24f, carPlaceholderPaint)
            textPaint.textSize = 44f
            textPaint.color = 0xFF94A3B8.toInt()
            textPaint.isFakeBoldText = true
            canvas.drawText("NEXA • PREMIUM AUTOMOTIVE", width / 2f, 650f, textPaint)
        }

        // Offer Headline Banner
        val offerBoxPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = 0xFF1E3A8A.toInt()
        }
        canvas.drawRoundRect(RectF(80f, 870f, width - 80f, 980f), 24f, 24f, offerBoxPaint)
        val offerBorder = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 4f
            color = 0xFF38BDF8.toInt()
        }
        canvas.drawRoundRect(RectF(80f, 870f, width - 80f, 980f), 24f, 24f, offerBorder)

        textPaint.color = 0xFFFBBF24.toInt()
        textPaint.textSize = 46f
        textPaint.isFakeBoldText = true
        canvas.drawText(offerHeadline.uppercase(), width / 2f, 940f, textPaint)

        // Price Callout
        textPaint.color = 0xFFFFFFFF.toInt()
        textPaint.textSize = 34f
        textPaint.isFakeBoldText = true
        canvas.drawText("Starting at $startingPrice | Fast Delivery in Sasaram", width / 2f, 1030f, textPaint)

        // Divider
        val divPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = 0x44CBD5E1.toInt()
            strokeWidth = 2f
        }
        canvas.drawLine(100f, 1070f, width - 100f, 1070f, divPaint)

        // Relationship Manager & Contact Section
        val footerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = 0xFF0D1E36.toInt()
        }
        canvas.drawRoundRect(RectF(60f, 1100f, width - 60f, 1290f), 24f, 24f, footerPaint)

        textPaint.color = 0xFF94A3B8.toInt()
        textPaint.textSize = 26f
        textPaint.isFakeBoldText = false
        canvas.drawText("YOUR DEDICATED RELATIONSHIP MANAGER", width / 2f, 1145f, textPaint)

        textPaint.color = 0xFFFFFFFF.toInt()
        textPaint.textSize = 42f
        textPaint.isFakeBoldText = true
        canvas.drawText(rmName.uppercase(), width / 2f, 1195f, textPaint)

        textPaint.color = 0xFF25D366.toInt()
        textPaint.textSize = 38f
        textPaint.isFakeBoldText = true
        canvas.drawText("Call & WhatsApp: +91 $rmPhone", width / 2f, 1245f, textPaint)

        // Footer disclaimer
        textPaint.color = 0xFF64748B.toInt()
        textPaint.textSize = 20f
        textPaint.isFakeBoldText = false
        canvas.drawText("*Terms & conditions apply. Offer valid at Pearl Cars Sasaram for limited period.", width / 2f, 1320f, textPaint)

        return bitmap
    }
}
