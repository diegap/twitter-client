package katas.client.twitter.signup.domain.entities

data class User(val realName: String, val nickname: String, val follows: Set<String>) {
    fun addFollow(follow: String) = this.copy(follows = follows.plusElement(follow))
}