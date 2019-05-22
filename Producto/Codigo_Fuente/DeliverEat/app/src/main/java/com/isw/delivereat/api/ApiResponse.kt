package com.isw.delivereat.api

class ApiResponse<T>(
    @Suppress("KDocMissingDocumentation")
    val data: T? = null,
    error: Throwable?,
    status: Status?
) {
    companion object {
        /**
         * Creates a [FormApiResponse] with [Status.SUCCESS] and data value from parameter
         *
         * @param form data fetched
         */
        fun <T> success(data: T): ApiResponse<T> = ApiResponse(data, null, Status.SUCCESS)

        /**
         * Creates a [FormApiResponse] with [Status.ERROR], null data and the [Throwable] error.
         *
         * @param error value
         */
        fun <T> error(throwable: Throwable): ApiResponse<T> =
            ApiResponse(null, throwable, Status.ERROR)

        /**
         * Creates a [FormApiResponse] with [Status.LOADING]. [form] and [error] null
         */
        fun <T> loading(): ApiResponse<T> = ApiResponse(null, null, Status.LOADING)
    }
}
