package com.example.cryptocurrency.common

sealed class DomainException(message: String) : Exception(message) {
    class DomainNetworkException(message: String) : DomainException(message)
}