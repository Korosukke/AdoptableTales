package com.example.ca3

class PetDataModel(image: Int?, name: String?, type: String?, desc: String?) {

    private var image: Int?
    var name: String?
    var type: String?
    private var desc: String?

    init {
        this.image = image
        this.name = name
        this.type = type
        this.desc = desc
    }

    fun getPetImage(): Int? {
        return image
    }

    fun getPetName(): String? {
        return name
    }

    fun getPetType(): String? {
        return type
    }


    fun getPetDesc(): String? {
        return desc
    }
}
