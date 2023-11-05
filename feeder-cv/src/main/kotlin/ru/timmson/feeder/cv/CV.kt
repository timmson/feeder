package ru.timmson.feeder.cv

class CV {
    var url: String = ""
    var name: String = ""
    var type: String = ""
    var title: String = ""
    var area: String = ""

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CV

        if (name != other.name) return false
        //if (type != other.type) return false
        if (title != other.title) return false
        if (area != other.area) return false

        return true
    }

    override fun hashCode(): Int {
        var result = url.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + area.hashCode()
        return result
    }

    override fun toString(): String {
        return "CV(url='$url', name='$name', type='$type', title='$title', area='$area')"
    }
}
