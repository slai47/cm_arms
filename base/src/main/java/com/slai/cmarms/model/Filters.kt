package com.slai.cmarms.model

import com.slai.cmarms.R


enum class Action(val titleId : Int, val value : String) {

    BOLT_ACTION(R.string.action_bolt, "bolt-action"),
    BREAK_OPEN(R.string.action_break_open, "break-open"),
    DOUBLE_SINGLE_ACTION(R.string.action_double_single, "double-single-action"),

    DOUBLE_ACTION(R.string.action_double, "double-action"),
    LEVER_ACTION(R.string.action_lever, "lever-action"),
    OVER_AND_UNDER(R.string.action_over_under, "over-under"),

    PUMP_ACTION(R.string.action_pump, "pump-action"),
    SEMI_AUTO(R.string.action_semi, "semi-automatic"),
    SIDE_BY_SIDE(R.string.action_side_side, "side-side"),

    SINGLE_ACTION(R.string.action_single, "single-action"),
    SINGLE_SHOT(R.string.action_single_shot, "single-shot"),
    STRIKER_FIRE(R.string.action_striker, "striker-fire")

}

enum class FirearmType(val titleId : Int, val value : String) {

    DERRINGER(R.string.type_derringer, "derringer"),
    MUZZLE_LOADER(R.string.type_muzzle, "muzzle-loader"),
    PISTOL(R.string.type_pistol, "pistol"),
    REVOLVER(R.string.type_revolver, "revolver"),
    RIFLE(R.string.type_rifle, "rifle"),
    SHOTGUN(R.string.type_shotgun, "shotgun"),
    SUPPRESSORS(R.string.type_suppressors, "suppressors")


}