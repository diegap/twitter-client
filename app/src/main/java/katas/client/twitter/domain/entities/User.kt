package katas.client.twitter.domain.entities

data class User(val realName: String, val nickname: String, val follows: Set<String>)