package com.evolutivecode.admin.portal.infra.externalservice

import com.amazonaws.services.s3.AmazonS3
import com.evolutivecode.opccadminportal.common.util.FileUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Component
class StorageExternalService(
        @Value("\${storage.service.default.location}") private val defaultLocation: String,
        @Value("\${aws.bucket.name}") private val bucket: String,
        @Value("\${aws.bucket.location.public}") private val publicLocation: String,
        private val s3Client: AmazonS3
) {

    val logger: Logger = LoggerFactory.getLogger(StorageExternalService::class.java)

    private val fileUtil = FileUtil()

    fun store(multipartFile: MultipartFile, directoryName: String, name: String?): String {
        logger.info("start storage file orignalName : ${multipartFile.originalFilename} withe the name of $name")

        // normalize the file path
        val fileName = name?: StringUtils.cleanPath(multipartFile.originalFilename!!)
        logger.info("uploading file $fileName")

        val file: File? = fileUtil.createIFile(multipartFile, directoryName, fileName, defaultLocation)

        /* loading image to S3 */
        val destination = "$publicLocation/$directoryName/$fileName"
        val result = s3Client.putObject(bucket, destination, file)
        logger.info("*** MultipartFile *** " + multipartFile.name + " : " + file!!.totalSpace)
        file.delete()

        return fileName
    }

    fun storePublic(file: File, filename: String) : String{
        return try {
            /* loading image to S3 */
            val destination = "$publicLocation/$filename"
            val result = s3Client.putObject(bucket, destination, file)
            logger.info("store status  : ${result.hashCode()}")
            file.name
        }
        catch (e: Exception){
            logger.info("error occurred while uploading file to S3 server...: ${e.message}")
            ""
        }
    }

    fun storePublic(multipartFile: MultipartFile, filename: String) : String{
        return try {
            /* loading image to S3 */
            val destination = "$publicLocation/$filename"
            val file: File? = fileUtil.createIFile(multipartFile, "temp", filename, defaultLocation)
            val result = s3Client.putObject(bucket, destination, file)
            logger.info("store status  : ${result.hashCode()}")
            file?.delete()

            multipartFile.originalFilename!!
        }
        catch (e: Exception){
            logger.info("error occurred while uploading file to S3 server... : ${e.message}")
            multipartFile.originalFilename!!
        }
    }


    private fun multipartToFile(multipartFile: MultipartFile) : File{
        val file = File(multipartFile.originalFilename!!)
        multipartFile.transferTo(file)
        return file
    }

}
