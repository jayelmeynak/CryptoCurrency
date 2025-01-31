package com.example.cryptocurrency.data.remote.dto

import com.example.cryptocurrency.domain.model.TeamMember
import com.google.gson.annotations.SerializedName

data class TeamMemberDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("position")
    val position: String,

)

fun TeamMemberDto.toTeamMember() = TeamMember(
    id = id,
    name = name,
    position = position
)