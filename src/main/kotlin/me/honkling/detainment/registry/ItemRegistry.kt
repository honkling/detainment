package me.honkling.detainment.registry

import me.honkling.detainment.item.Item

class ItemRegistry : SimpleRegistry<Item>("item", Item::class.java)