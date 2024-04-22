package com.saas.bff.oil.code

import lombok.ToString

@ToString
enum class Region(val code: String, val fullName: String) {
    전국("00", "전국"),
    서울("01", "서울"),
    경기("02", "경기"),
    강원("03", "강원"),
    충북("04", "충북"),
    충남("05", "충남"),
    전북("06", "전북"),
    전남("07", "전남"),
    경북("08", "경북"),
    경남("09", "경남"),
    부산("10", "부산"),
    제주("11", "제주"),
    대구("14", "대구"),
    인천("15", "인천"),
    광주("16", "광주"),
    대전("17", "대전"),
    울산("18", "울산"),
    세종("19", "세종");

    companion object {
        fun findCode(address: String): Region {
            val regionNames = entries.map { it.fullName }
            val foundRegionName = regionNames.find { address.contains(it) }
            return entries.first { it.fullName == foundRegionName }
        }
    }
}
