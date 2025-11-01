package com.example.vplabweek78_soal2.data.dto

data class ArtistDto(
    val idArtist: String,
    val strArtist: String,
    val strGenre: String,
    val strBiographyEN: String,
    val strArtistThumb: String?,
    val strArtistBanner: String?
)

data class ArtistAlbumsResponse(
    val album: List<AlbumSimpleDto>?
)

data class AlbumSimpleDto(
    val idAlbum: String,
    val strAlbum: String,
    val intYearReleased: String,
    val strAlbumThumb: String?
)

data class AlbumDetailResponse(
    val album: List<AlbumDetailDto>?
)

data class ArtistResponse(
    val artists: List<ArtistDto>?
)

data class AlbumDetailDto(
    val idAlbum: String,
    val strAlbum: String,
    val strArtist: String,
    val intYearReleased: String,
    val strGenre: String,
    val strDescriptionEN: String,
    val strAlbumThumb: String?
)

data class AlbumTracksResponse(
    val track: List<TrackDto>?
)

data class TrackDto(
    val idTrack: String,
    val strTrack: String,
    val intDuration: String
)