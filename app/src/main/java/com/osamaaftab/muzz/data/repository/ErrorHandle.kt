package com.osamaaftab.muzz.data.repository

import com.osamaaftab.muzz.domain.model.ErrorModel

class ErrorHandle {

    fun traceErrorException(throwable: Throwable?): ErrorModel {
        return ErrorModel(throwable?.message, 0, ErrorModel.ErrorStatus.BAD_RESPONSE)
    }
}