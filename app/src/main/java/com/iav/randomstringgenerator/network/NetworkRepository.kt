package com.iav.randomstringgenerator.network


import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import com.iav.randomstringgenerator.data.remote.response.random_string_res.RandomString
import org.json.JSONObject

class NetworkRepository(private val contentResolver: ContentResolver) {

    fun generateString(): Result<RandomString> {
        return try {
            val uri = Uri.parse("content://com.iav.contestdataprovider/text")
            val projection = arrayOf("data")

            val bundle = Bundle().apply {
                putInt(ContentResolver.QUERY_ARG_LIMIT, 1)
            }

            val cursor = contentResolver.query(uri, projection, bundle, null)
                ?: return Result.failure(Exception("No cursor returned"))

            return cursor.use {
                if (it.moveToFirst()) {
                    val jsonString = it.getString(it.getColumnIndexOrThrow("data"))
                    val json = JSONObject(jsonString).getJSONObject("randomText")
                    Result.success(
                        RandomString(
                            value = json.getString("value"),
                            length = json.getInt("length"),
                            created = json.getString("created")
                        )
                    )
                } else {
                    Result.failure(Exception("No data returned"))
                }
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
