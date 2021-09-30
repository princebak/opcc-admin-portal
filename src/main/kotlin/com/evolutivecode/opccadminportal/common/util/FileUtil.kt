package com.evolutivecode.opccadminportal.common.util

import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.File
import java.io.FilenameFilter
import java.io.IOException
import java.nio.file.Files
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.imageio.ImageIO
import kotlin.jvm.Throws


class FileUtil {

    val STRING_LENGTH = 10
    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    val EXTENSIONS = arrayOf(
            "png", "jpeg", "jpg"
    )

    val MAX_WIDTH = 1920f
    val MAX_HEIGHT = 1080f

    val FILE_FILTER = FilenameFilter { dir, name ->
        for (ext in EXTENSIONS) {
            if (name.endsWith(".$ext")) {
                return@FilenameFilter true
            }
        }
        false
    }

    @Throws(IOException::class)
    fun createIFile(fileToCreate: MultipartFile, direct: String, name: String?, location: String): File? {
        val dir = File(location + direct)
        if (!dir.exists()) {
            dir.mkdir()
        }
        val file = File(dir, name)

        fileToCreate.inputStream.use { `is` -> Files.copy(`is`, file.toPath()) }

        return file
    }

    @Throws(IOException::class)
    fun resizeFile(image: MultipartFile): BufferedImage? {
        return ImageIO.read(image.inputStream)
    }

    fun getImagesNames(directoryPath: String, refNo: String): List<String>? {
        val imagesNames: MutableList<String> = ArrayList()
        val dir = File(directoryPath + refNo)
        // filter to identify images based on their extensions
        if (dir.isDirectory) { // make sure it's a directory
            for (f in dir.listFiles()) {
                imagesNames.add(f.name)
            }
            return imagesNames
        }
        return null
    }

    @Throws(IOException::class)
    fun convert(file: MultipartFile): File? {
        val convFile = File(file.originalFilename!!)
        convFile.createNewFile()
        file.inputStream.use { `is` -> Files.copy(`is`, convFile.toPath()) }
        return convFile
    }

    fun createDirectory(directoryName: String): String{
        val date = Calendar.getInstance().time
        val dateFormat: DateFormat = SimpleDateFormat("yyyymmddhhmmss")
        return dateFormat.format(date).plus("-").plus(directoryName)
    }

    fun generateRandomString(): String {
        return (1..STRING_LENGTH)
                .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("")
    }
}
