package com.slai.cmarms

import com.slai.cmarms.model.*

class FiltersDataHolder {

    lateinit var _actions: ArrayList<Filter>
    var actions : ArrayList<Filter>
        get(){
            if(!::_actions.isInitialized) {
                _actions = java.util.ArrayList()

                _actions.add(Filter(R.string.action_bolt, "bolt-action"))
                _actions.add(Filter(R.string.action_break_open, "break-open"))
                _actions.add(Filter(R.string.action_double_single, "double-single-action"))
                _actions.add(Filter(R.string.action_double, "double-action"))
                _actions.add(Filter(R.string.action_lever, "lever-action"))
                _actions.add(Filter(R.string.action_over_under, "over-under"))
                _actions.add(Filter(R.string.action_pump, "pump-action"))
                _actions.add(Filter(R.string.action_semi, "semi-automatic"))
                _actions.add(Filter(R.string.action_side_side, "side-side"))
                _actions.add(Filter(R.string.action_single, "single-action"))
                _actions.add(Filter(R.string.action_single_shot, "single-shot"))
                _actions.add(Filter(R.string.action_striker, "striker-fire"))

            }
            return _actions
        }
        set(value) {
            _actions = value
        }

    lateinit var _categories : ArrayList<Filter>
    var categories : ArrayList<Filter>
        get() {
            if(!::_categories.isInitialized){
                _categories = ArrayList()

                _categories.add(Filter(R.string.category_firearms , "guns", true))
                _categories.add(Filter(R.string.category_antique , "antiques"))
                _categories.add(Filter(R.string.category_handguns , "handguns"))
                _categories.add(Filter(R.string.category_muzzle_loaders , "muzzle-loaders"))
                _categories.add(Filter(R.string.category_nfa , "nfa-firearms"))
                _categories.add(Filter(R.string.category_rifles , "rifles"))
                _categories.add(Filter(R.string.category_shotgun , "shotguns"))

                _categories.add(Filter(R.string.category_accessories , "firearm-accessories", true))
                _categories.add(Filter(R.string.category_ammo , "ammo"))
                _categories.add(Filter(R.string.category_gun_parts , "gun-parts"))
                _categories.add(Filter(R.string.category_gun_safes , "gun-safes"))
                _categories.add(Filter(R.string.category_holsters , "holsters"))
                _categories.add(Filter(R.string.category_magazines , "magazines"))
                _categories.add(Filter(R.string.category_optics , "optics"))
                _categories.add(Filter(R.string.category_reloading , "reloading"))
                _categories.add(Filter(R.string.category_tactical_gear , "tactical-gear"))

                _categories.add(Filter(R.string.category_outdoor_gear , "outdoors", true))
                _categories.add(Filter(R.string.category_archery , "archery"))
                _categories.add(Filter(R.string.category_fishing , "fishing"))
                _categories.add(Filter(R.string.category_hunting , "hunting"))
                _categories.add(Filter(R.string.category_knives , "knives"))
                _categories.add(Filter(R.string.category_range_targets , "targets-and-range-equipment"))

                _categories.add(Filter(R.string.category_other_items , "other", true))
                _categories.add(Filter(R.string.category_air_guns , "air-guns"))
                _categories.add(Filter(R.string.category_farming , "farming"))
                _categories.add(Filter(R.string.category_mis , "misc"))
                _categories.add(Filter(R.string.category_paintball , "paintball"))
                _categories.add(Filter(R.string.category_vehicles , "vehicles"))

            }
            return _categories
        }
    set(value) {
        _categories = value
    }

    lateinit var _calibers : ArrayList<Filter>
    var calibers : ArrayList<Filter>
        get() {
            if(!::_calibers.isInitialized){
                _calibers = ArrayList<Filter>()
                _calibers.add(Filter(R.string.caliber_10_gauge, "10-gauge"))
                _calibers.add(Filter(R.string.caliber_10_mm, "10-mm"))
                _calibers.add(Filter(R.string.caliber_12, "12-gauge"))
                _calibers.add(Filter(R.string.caliber_16, "16-gauge"))
                _calibers.add(Filter(R.string.caliber_17_two, "17-hm2"))
                _calibers.add(Filter(R.string.caliber_17_r, "17-hmr"))
                _calibers.add(Filter(R.string.caliber_22_hornet, ""))
                _calibers.add(Filter(R.string.caliber_17_remington, ""))
                _calibers.add(Filter(R.string.caliber_17_remington_fireball, ""))
                _calibers.add(Filter(R.string.caliber_17_WSM, ""))

                _calibers.add(Filter(R.string.caliber_20_gauge, ""))
                _calibers.add(Filter(R.string.caliber_204, ""))
                _calibers.add(Filter(R.string.caliber_22_hornet, ""))
                _calibers.add(Filter(R.string.caliber_22_long_rifle, ""))
                _calibers.add(Filter(R.string.caliber_22_magnum, ""))
                _calibers.add(Filter(R.string.caliber_22_short, ""))
                _calibers.add(Filter(R.string.caliber_22_WMR, ""))
                _calibers.add(Filter(R.string.caliber_22_250_remington, ""))
                _calibers.add(Filter(R.string.caliber_220, ""))
                _calibers.add(Filter(R.string.caliber_221, ""))
                _calibers.add(Filter(R.string.caliber_222, ""))
                _calibers.add(Filter(R.string.caliber_223_WSSM, ""))
                _calibers.add(Filter(R.string.caliber_223_wylde, ""))
                _calibers.add(Filter(R.string.caliber_223_556, ""))
                _calibers.add(Filter(R.string.caliber_224, ""))
                _calibers.add(Filter(R.string.caliber_224_weatherby, ""))
                _calibers.add(Filter(R.string.caliber_225, ""))
                _calibers.add(Filter(R.string.caliber_240, ""))
                _calibers.add(Filter(R.string.caliber_243, ""))
                _calibers.add(Filter(R.string.caliber_25_acp, ""))
                _calibers.add(Filter(R.string.caliber_25_wssm, ""))
                _calibers.add(Filter(R.string.caliber_25_06, ""))
                _calibers.add(Filter(R.string.caliber_25_20, ""))
                _calibers.add(Filter(R.string.caliber_257, ""))
                _calibers.add(Filter(R.string.caliber_257_weatherby, ""))
                _calibers.add(Filter(R.string.caliber_260, ""))
                _calibers.add(Filter(R.string.caliber_264, ""))
                _calibers.add(Filter(R.string.caliber_270_weatherby, ""))
                _calibers.add(Filter(R.string.caliber_270, ""))
                _calibers.add(Filter(R.string.caliber_270_wsm, ""))
                _calibers.add(Filter(R.string.caliber_28_gauge, ""))
                _calibers.add(Filter(R.string.caliber_280, ""))
                _calibers.add(Filter(R.string.caliber_280_remington, ""))

                _calibers.add(Filter(R.string.caliber_30_carbine, ""))
                _calibers.add(Filter(R.string.caliber_30_luger, ""))
                _calibers.add(Filter(R.string.caliber_30_thompson, ""))
                _calibers.add(Filter(R.string.caliber_30_06, ""))
                _calibers.add(Filter(R.string.caliber_30_30, ""))
                _calibers.add(Filter(R.string.caliber_30_378, ""))
                _calibers.add(Filter(R.string.caliber_30_40, ""))
                _calibers.add(Filter(R.string.caliber_300_blackout, ""))
                _calibers.add(Filter(R.string.caliber_300_magnum, ""))
                _calibers.add(Filter(R.string.caliber_300_remington_short, ""))
                _calibers.add(Filter(R.string.caliber_300_remington, ""))
                _calibers.add(Filter(R.string.caliber_300_ruger, ""))
                _calibers.add(Filter(R.string.caliber_300_savage, ""))
                _calibers.add(Filter(R.string.caliber_300_weatherby, ""))
                _calibers.add(Filter(R.string.caliber_300_winchester, ""))
                _calibers.add(Filter(R.string.caliber_300_winchester_short, ""))
                _calibers.add(Filter(R.string.caliber_303, ""))
                _calibers.add(Filter(R.string.caliber_307, ""))
                _calibers.add(Filter(R.string.caliber_308, ""))
                _calibers.add(Filter(R.string.caliber_308_winchester, ""))
                _calibers.add(Filter(R.string.caliber_308_762, ""))
                _calibers.add(Filter(R.string.caliber_32_ACP, ""))
                _calibers.add(Filter(R.string.caliber_32_NAA, ""))
                _calibers.add(Filter(R.string.caliber_32_SW, ""))
                _calibers.add(Filter(R.string.caliber_32_SW_long, ""))
                _calibers.add(Filter(R.string.caliber_32_short, ""))
                _calibers.add(Filter(R.string.caliber_32_winchester, ""))
                _calibers.add(Filter(R.string.caliber_32_20, ""))
                _calibers.add(Filter(R.string.caliber_32_40, ""))
                _calibers.add(Filter(R.string.caliber_325, ""))
                _calibers.add(Filter(R.string.caliber_327, ""))
                _calibers.add(Filter(R.string.caliber_338, ""))
                _calibers.add(Filter(R.string.caliber_338_lapue, ""))
                _calibers.add(Filter(R.string.caliber_338_marlin, ""))
                _calibers.add(Filter(R.string.caliber_338_remington, ""))
                _calibers.add(Filter(R.string.caliber_338_ruger, ""))
                _calibers.add(Filter(R.string.caliber_338_winchester, ""))
                _calibers.add(Filter(R.string.caliber_338_378, ""))
                _calibers.add(Filter(R.string.caliber_340, ""))
                _calibers.add(Filter(R.string.caliber_348, ""))
                _calibers.add(Filter(R.string.caliber_35_remington, ""))

                _calibers.add(Filter(R.string.caliber_35_whelen, ""))
                _calibers.add(Filter(R.string.caliber_350, ""))
                _calibers.add(Filter(R.string.caliber_356, ""))
                _calibers.add(Filter(R.string.caliber_357, ""))
                _calibers.add(Filter(R.string.caliber_357_sig, ""))
                _calibers.add(Filter(R.string.caliber_358, ""))
                _calibers.add(Filter(R.string.caliber_36, ""))
                _calibers.add(Filter(R.string.caliber_370, ""))
                _calibers.add(Filter(R.string.caliber_375, ""))
                _calibers.add(Filter(R.string.caliber_375_remington, ""))
                _calibers.add(Filter(R.string.caliber_375_ruger, ""))
                _calibers.add(Filter(R.string.caliber_375_weatherby, ""))
                _calibers.add(Filter(R.string.caliber_375_winchester, ""))
                _calibers.add(Filter(R.string.caliber_376, ""))
                _calibers.add(Filter(R.string.caliber_378, ""))
                _calibers.add(Filter(R.string.caliber_38, ""))
                _calibers.add(Filter(R.string.caliber_38_special, ""))
                _calibers.add(Filter(R.string.caliber_38_short, ""))
                _calibers.add(Filter(R.string.caliber_38_super, ""))
                _calibers.add(Filter(R.string.caliber_38_40, ""))
                _calibers.add(Filter(R.string.caliber_38_55, ""))
                _calibers.add(Filter(R.string.caliber_380, ""))

                _calibers.add(Filter(R.string.caliber_46_30, ""))
                _calibers.add(Filter(R.string.caliber_40, ""))
                _calibers.add(Filter(R.string.caliber_40_sw, ""))
                _calibers.add(Filter(R.string.caliber_400, ""))
                _calibers.add(Filter(R.string.caliber_404, ""))
                _calibers.add(Filter(R.string.caliber_405, ""))
                _calibers.add(Filter(R.string.caliber_41, ""))
                _calibers.add(Filter(R.string.caliber_41_remington, ""))
                _calibers.add(Filter(R.string.caliber_410, ""))
                _calibers.add(Filter(R.string.caliber_416, ""))
                _calibers.add(Filter(R.string.caliber_416_rigby, ""))
                _calibers.add(Filter(R.string.caliber_416_ruger, ""))
                _calibers.add(Filter(R.string.caliber_416_weatherby, ""))
                _calibers.add(Filter(R.string.caliber_44_black, ""))
                _calibers.add(Filter(R.string.caliber_44_magnum, ""))
                _calibers.add(Filter(R.string.caliber_44_rimfire, ""))
                _calibers.add(Filter(R.string.caliber_44_special, ""))
                _calibers.add(Filter(R.string.caliber_44_70, ""))
                _calibers.add(Filter(R.string.caliber_444, ""))
                _calibers.add(Filter(R.string.caliber_45_acp, ""))
                _calibers.add(Filter(R.string.caliber_45_acp, ""))
                _calibers.add(Filter(R.string.caliber_45_auto, ""))
                _calibers.add(Filter(R.string.caliber_45_black, ""))
                _calibers.add(Filter(R.string.caliber_45_colt, ""))
                _calibers.add(Filter(R.string.caliber_45_gap, ""))
                _calibers.add(Filter(R.string.caliber_45_long, ""))
                _calibers.add(Filter(R.string.caliber_45_100, ""))
                _calibers.add(Filter(R.string.caliber_45_70, ""))
                _calibers.add(Filter(R.string.caliber_45_90, ""))
                _calibers.add(Filter(R.string.caliber_450, ""))
                _calibers.add(Filter(R.string.caliber_450_marlin, ""))
                _calibers.add(Filter(R.string.caliber_450_nitro, ""))
                _calibers.add(Filter(R.string.caliber_450_rigby, ""))
                _calibers.add(Filter(R.string.caliber_450_400, ""))
                _calibers.add(Filter(R.string.caliber_454, ""))
                _calibers.add(Filter(R.string.caliber_458, ""))
                _calibers.add(Filter(R.string.caliber_458_socom, ""))
                _calibers.add(Filter(R.string.caliber_458_winchester, ""))
                _calibers.add(Filter(R.string.caliber_460, ""))
                _calibers.add(Filter(R.string.caliber_460_weatherby, ""))
                _calibers.add(Filter(R.string.caliber_470, ""))
                _calibers.add(Filter(R.string.caliber_475, ""))
                _calibers.add(Filter(R.string.caliber_480, ""))

                _calibers.add(Filter(R.string.caliber_545, ""))
                _calibers.add(Filter(R.string.caliber_556, ""))
                _calibers.add(Filter(R.string.caliber_56, ""))
                _calibers.add(Filter(R.string.caliber_57, ""))
                _calibers.add(Filter(R.string.caliber_50, ""))
                _calibers.add(Filter(R.string.caliber_50_beowulf, ""))
                _calibers.add(Filter(R.string.caliber_50_black, ""))
                _calibers.add(Filter(R.string.caliber_50_BMG, ""))
                _calibers.add(Filter(R.string.caliber_500_nitro, ""))
                _calibers.add(Filter(R.string.caliber_500_sw, ""))
                _calibers.add(Filter(R.string.caliber_505, ""))
                _calibers.add(Filter(R.string.caliber_52, ""))
                _calibers.add(Filter(R.string.caliber_54, ""))
                _calibers.add(Filter(R.string.caliber_58, ""))

                _calibers.add(Filter(R.string.caliber_6_5, ""))
                _calibers.add(Filter(R.string.caliber_6_5_grendal, ""))
                _calibers.add(Filter(R.string.caliber_6_5_284, ""))
                _calibers.add(Filter(R.string.caliber_6_5_jp, ""))
                _calibers.add(Filter(R.string.caliber_6_5_carcano, ""))
                _calibers.add(Filter(R.string.caliber_6_5_swedish, ""))
                _calibers.add(Filter(R.string.caliber_6_8_spcii, ""))
                _calibers.add(Filter(R.string.caliber_6mm, ""))
                _calibers.add(Filter(R.string.caliber_6mm_remington, ""))

                _calibers.add(Filter(R.string.caliber_75_swiss, ""))
                _calibers.add(Filter(R.string.caliber_76, ""))
                _calibers.add(Filter(R.string.caliber_762, ""))
                _calibers.add(Filter(R.string.caliber_762_nato, ""))
                _calibers.add(Filter(R.string.caliber_762r, ""))
                _calibers.add(Filter(R.string.caliber_765, ""))
                _calibers.add(Filter(R.string.caliber_77, ""))
                _calibers.add(Filter(R.string.caliber_7mm, ""))
                _calibers.add(Filter(R.string.caliber_7mm_remington, ""))
                _calibers.add(Filter(R.string.caliber_7mm_remington_sa, ""))
                _calibers.add(Filter(R.string.caliber_7mm_remington_ultra, ""))
                _calibers.add(Filter(R.string.caliber_7mm_westerner, ""))
                _calibers.add(Filter(R.string.caliber_7mm_weatherby, ""))
                _calibers.add(Filter(R.string.caliber_7mm_winchester, ""))
                _calibers.add(Filter(R.string.caliber_7mm_08, ""))
                _calibers.add(Filter(R.string.caliber_757, ""))
                _calibers.add(Filter(R.string.caliber_764, ""))
                _calibers.add(Filter(R.string.caliber_765r, ""))

                _calibers.add(Filter(R.string.caliber_8mm, ""))
                _calibers.add(Filter(R.string.caliber_8mm_remington, ""))
                _calibers.add(Filter(R.string.caliber_856, ""))
                _calibers.add(Filter(R.string.caliber_857, ""))
                _calibers.add(Filter(R.string.caliber_857_mauser, ""))

                _calibers.add(Filter(R.string.caliber_93_62, ""))
                _calibers.add(Filter(R.string.caliber_93_72, ""))
                _calibers.add(Filter(R.string.caliber_93_74, ""))
                _calibers.add(Filter(R.string.caliber_9mm, ""))
                _calibers.add(Filter(R.string.caliber_9mm_makarov, ""))
                _calibers.add(Filter(R.string.caliber_9_21, ""))
                _calibers.add(Filter(R.string.caliber_9_23_winchester, ""))
                _calibers.add(Filter(R.string.caliber_9_23, ""))
            }
            return _calibers
        }
        set(value) {
            _calibers = value
        }

    lateinit var _manufacturers : ArrayList<Filter>
    var manufacturers : ArrayList<Filter>
        get() {
            if(!::_calibers.isInitialized){

            }
            return _manufacturers
        }
        set(value) {
            _manufacturers = value
        }

    lateinit var _firearmTypes : ArrayList<Filter>
    var firearmTypes : ArrayList<Filter>
        get() {
            if(!::_firearmTypes.isInitialized){
                _firearmTypes = ArrayList()
                _firearmTypes.add(Filter(R.string.type_derringer, "derringer"))
                _firearmTypes.add(Filter(R.string.type_muzzle, "muzzle-loader"))
                _firearmTypes.add(Filter(R.string.type_pistol, "pistol"))
                _firearmTypes.add(Filter(R.string.type_revolver, "revolver"))
                _firearmTypes.add(Filter(R.string.type_rifle, "rifle"))
                _firearmTypes.add(Filter(R.string.type_shotgun, "shotgun"))
                _firearmTypes.add(Filter(R.string.type_suppressors, "suppressors"))
            }
            return _firearmTypes
        }
        set(value) {
            _firearmTypes = value
        }
}