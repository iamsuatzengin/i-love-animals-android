package com.suatzengin.iloveanimals.domain.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}
