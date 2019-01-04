package com.slai.cmarms.model

data class NavigationTransitionEvent(val navTo : NavigationEvent)

enum class NavigationEvent {
    FEED,
    SEARCH,
    SETTINGS
}

data class ProgressEvent(val showProgress : Boolean)

data class FilterDialogClosed(val title : String)