package my.krzyjan.documentmgr

data class ApplicationDatabase(
    val path: String,
    val host: String = "localhost",
    val port: Int = -1,
    val user: String = "",
    val pass: String = "",
    val urlExtra: String = "",
    val debug: String = ""
)
data class ApplicationConfig(val database: ApplicationDatabase)
