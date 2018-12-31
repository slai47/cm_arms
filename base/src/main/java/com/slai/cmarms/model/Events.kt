package com.slai.cmarms.model

data class NavigationTransitionEvent(val navTo : NavigationEvent)

enum class NavigationEvent {
    FEED,
    SEARCH,
    SETTINGS
}