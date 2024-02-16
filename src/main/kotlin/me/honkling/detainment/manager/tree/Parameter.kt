package me.honkling.detainment.manager.tree

data class Parameter(
        val name: String,
        val type: Class<*>,
        val required: Boolean,
        val greedy: Boolean
)
