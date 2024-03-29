package my.krzyjan.documentmgr.model

object TestConstants {
    const val DOCUMENT_NAME = "Gas Bill 2023 April"
    const val NEW_DOCUMENT_NAME = "Gas Bill 2023/04"
    const val DOCUMENT_PATH = "C:/Document/Home/Bill2/2023"

    val someDocuments = listOf(
        Document("202301 Gas Bill", "C:/Documents/2023/Gas/202301.pdf"),
        Document("202302 Water Bill", "C:/Documents/2023/Water/202302.pdf"),
        Document("202301 Electricity Bill", "C:/Documents/2023/Electricity/202301.pdf"),
        Document("202303 Water Bill", "C:/Documents/2023/Water/202303.pdf"),
        Document("202304 Gas Bill", "C:/Documents/2023/Gas/202304.pdf"),
        Document("202305 Electricity Bill", "C:/Documents/2023/Electricity/202305.pdf"),
        Document("202306 Water Bill", "C:/Documents/2023/Water/202306.pdf"),
        Document("202307 Gas Bill", "C:/Documents/2023/Gas/202307.pdf"),
    )
}
