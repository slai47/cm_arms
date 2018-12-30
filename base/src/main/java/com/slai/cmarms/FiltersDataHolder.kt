package com.slai.cmarms

import com.slai.cmarms.model.*

class FiltersDataHolder {

    private lateinit var _actions: ArrayList<Filter>
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

    private lateinit var _categories : ArrayList<Filter>
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

    private lateinit var _calibers : ArrayList<Filter>
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
                _calibers.add(Filter(R.string.caliber_17_hornet, "17-hornet"))
                _calibers.add(Filter(R.string.caliber_17_remington, "17-remington"))
                _calibers.add(Filter(R.string.caliber_17_remington_fireball, "17-fireball"))
                _calibers.add(Filter(R.string.caliber_17_WSM, "17-wsm"))

                _calibers.add(Filter(R.string.caliber_20_gauge, "20-gauge"))
                _calibers.add(Filter(R.string.caliber_204, "204-ruger"))
                _calibers.add(Filter(R.string.caliber_22_hornet, "22-hornet"))
                _calibers.add(Filter(R.string.caliber_22_long_rifle, "22-long-rifle"))
                _calibers.add(Filter(R.string.caliber_22_magnum, "22-magnum"))
                _calibers.add(Filter(R.string.caliber_22_short, "22-short"))
                _calibers.add(Filter(R.string.caliber_22_WMR, "22-wmr"))
                _calibers.add(Filter(R.string.caliber_22_250_remington, "22-250-rem"))
                _calibers.add(Filter(R.string.caliber_220, "220-swift"))
                _calibers.add(Filter(R.string.caliber_221, "221-fireball"))
                _calibers.add(Filter(R.string.caliber_222, "222-remington"))
                _calibers.add(Filter(R.string.caliber_223_WSSM, "223-wssm"))
                _calibers.add(Filter(R.string.caliber_223_wylde, "223-wylde"))
                _calibers.add(Filter(R.string.caliber_223_556, "223-556-nato"))
                _calibers.add(Filter(R.string.caliber_224, "224-valkyrie"))
                _calibers.add(Filter(R.string.caliber_224_weatherby, "224-weatherby"))
                _calibers.add(Filter(R.string.caliber_225, "224-win"))
                _calibers.add(Filter(R.string.caliber_240, "240-weatherby"))
                _calibers.add(Filter(R.string.caliber_243, "243-winchester"))
                _calibers.add(Filter(R.string.caliber_25_acp, "25-acp"))
                _calibers.add(Filter(R.string.caliber_25_wssm, "25-wssm"))
                _calibers.add(Filter(R.string.caliber_25_06, "25-06-rem"))
                _calibers.add(Filter(R.string.caliber_25_20, "25-25-win"))
                _calibers.add(Filter(R.string.caliber_257, "257-roberts"))
                _calibers.add(Filter(R.string.caliber_257_weatherby, "257-weatherby-magnum"))
                _calibers.add(Filter(R.string.caliber_260, "260-remington"))
                _calibers.add(Filter(R.string.caliber_264, "264-win-mag"))
                _calibers.add(Filter(R.string.caliber_270_weatherby, "270-weatherby"))
                _calibers.add(Filter(R.string.caliber_270, "270-winchester"))
                _calibers.add(Filter(R.string.caliber_270_wsm, "270-wsm"))
                _calibers.add(Filter(R.string.caliber_28_gauge, "28-gauge"))
                _calibers.add(Filter(R.string.caliber_280, "280-auckley"))
                _calibers.add(Filter(R.string.caliber_280_remington, "280-remington"))

                _calibers.add(Filter(R.string.caliber_30_carbine, "30-carbine"))
                _calibers.add(Filter(R.string.caliber_30_luger, "30-luger"))
                _calibers.add(Filter(R.string.caliber_30_thompson, "30-thompson-center"))
                _calibers.add(Filter(R.string.caliber_30_06, "30-06-springfield"))
                _calibers.add(Filter(R.string.caliber_30_30, "30-30-winchester"))
                _calibers.add(Filter(R.string.caliber_30_378, "30-378-weatherby"))
                _calibers.add(Filter(R.string.caliber_30_40, "30-40-krag"))
                _calibers.add(Filter(R.string.caliber_300_blackout, "300-blackout-7-62x35mm"))
                _calibers.add(Filter(R.string.caliber_300_magnum, "300-hh-magnum"))
                _calibers.add(Filter(R.string.caliber_300_remington_short, "300-rem-short-action"))
                _calibers.add(Filter(R.string.caliber_300_remington, "300-ultra-mag"))
                _calibers.add(Filter(R.string.caliber_300_ruger, "300-ruger-compact-mag"))
                _calibers.add(Filter(R.string.caliber_300_savage, "300-savage"))
                _calibers.add(Filter(R.string.caliber_300_weatherby, "300-weatherby-magnum"))
                _calibers.add(Filter(R.string.caliber_300_winchester, "300-win-magnum"))
                _calibers.add(Filter(R.string.caliber_300_winchester_short, "300-win-short-mag"))
                _calibers.add(Filter(R.string.caliber_303, "303-british"))
                _calibers.add(Filter(R.string.caliber_307, "307-winchester"))
                _calibers.add(Filter(R.string.caliber_308, "308-marlin-express"))
                _calibers.add(Filter(R.string.caliber_308_winchester, "308-winchester"))
                _calibers.add(Filter(R.string.caliber_308_762, "308-762-nato"))
                _calibers.add(Filter(R.string.caliber_32_ACP, "32-acp"))
                _calibers.add(Filter(R.string.caliber_32_NAA, "32-naa"))
                _calibers.add(Filter(R.string.caliber_32_SW, "32-sw"))
                _calibers.add(Filter(R.string.caliber_32_SW_long, "32-sw-long"))
                _calibers.add(Filter(R.string.caliber_32_short, "32-short-colt"))
                _calibers.add(Filter(R.string.caliber_32_winchester, "32-win-special"))
                _calibers.add(Filter(R.string.caliber_32_20, "32-20-win"))
                _calibers.add(Filter(R.string.caliber_32_40, "32-40-win"))
                _calibers.add(Filter(R.string.caliber_325, "325-win-short-mag"))
                _calibers.add(Filter(R.string.caliber_327, "327-magnum"))
                _calibers.add(Filter(R.string.caliber_338, "338-federal"))
                _calibers.add(Filter(R.string.caliber_338_lapue, "338-lapua"))
                _calibers.add(Filter(R.string.caliber_338_marlin, "338-marlin-express"))
                _calibers.add(Filter(R.string.caliber_338_remington, "338-rem-ultra-mag"))
                _calibers.add(Filter(R.string.caliber_338_ruger, "338-ruger"))
                _calibers.add(Filter(R.string.caliber_338_winchester, "338-win-mag"))
                _calibers.add(Filter(R.string.caliber_338_378, "338-378-weatherby"))
                _calibers.add(Filter(R.string.caliber_340, "340-weatherby"))
                _calibers.add(Filter(R.string.caliber_348, "348-winchester"))
                _calibers.add(Filter(R.string.caliber_35_remington, "35-remington"))

                _calibers.add(Filter(R.string.caliber_35_whelen, "35-whelen"))
                _calibers.add(Filter(R.string.caliber_350, "350-rem-mag"))
                _calibers.add(Filter(R.string.caliber_356, "356-winchester"))
                _calibers.add(Filter(R.string.caliber_357, "357-magnum"))
                _calibers.add(Filter(R.string.caliber_357_sig, "357-sig"))
                _calibers.add(Filter(R.string.caliber_358, "358-winchester"))
                _calibers.add(Filter(R.string.caliber_36, "36-black-powder"))
                _calibers.add(Filter(R.string.caliber_370, "370-sako"))
                _calibers.add(Filter(R.string.caliber_375, "375-magnum"))
                _calibers.add(Filter(R.string.caliber_375_remington, "375-ultra-mag"))
                _calibers.add(Filter(R.string.caliber_375_ruger, "375-ruger"))
                _calibers.add(Filter(R.string.caliber_375_weatherby, "375-weatherby-magnum"))
                _calibers.add(Filter(R.string.caliber_375_winchester, "375-winchester"))
                _calibers.add(Filter(R.string.caliber_376, "376-steyr"))
                _calibers.add(Filter(R.string.caliber_378, "378-weatherby-magnum"))
                _calibers.add(Filter(R.string.caliber_38, "38-sw"))
                _calibers.add(Filter(R.string.caliber_38_special, "38-special "))
                _calibers.add(Filter(R.string.caliber_38_short, "38-short"))
                _calibers.add(Filter(R.string.caliber_38_super, "38-super"))
                _calibers.add(Filter(R.string.caliber_38_40, "38-40-win"))
                _calibers.add(Filter(R.string.caliber_38_55, "38-55-winchester"))
                _calibers.add(Filter(R.string.caliber_380, "380-acp"))

                _calibers.add(Filter(R.string.caliber_46_30, "46x30-hk"))
                _calibers.add(Filter(R.string.caliber_40, "40cal"))
                _calibers.add(Filter(R.string.caliber_40_sw, "40-sw"))
                _calibers.add(Filter(R.string.caliber_400, "400-cor-bon"))
                _calibers.add(Filter(R.string.caliber_404, "404-jeffery"))
                _calibers.add(Filter(R.string.caliber_405, "405-winchester"))
                _calibers.add(Filter(R.string.caliber_41, "41-remington-magnum"))
                _calibers.add(Filter(R.string.caliber_41_remington, ".41-remington-magnum"))
                _calibers.add(Filter(R.string.caliber_410, "410-bore"))
                _calibers.add(Filter(R.string.caliber_416, "416-remington-magnum"))
                _calibers.add(Filter(R.string.caliber_416_rigby, "416-rigby"))
                _calibers.add(Filter(R.string.caliber_416_ruger, "416-ruger"))
                _calibers.add(Filter(R.string.caliber_416_weatherby, "416-weatherby-magnum"))
                _calibers.add(Filter(R.string.caliber_44_black, "44-black-powder"))
                _calibers.add(Filter(R.string.caliber_44_magnum, "44-magnum"))
                _calibers.add(Filter(R.string.caliber_44_rimfire, "44-rimfire"))
                _calibers.add(Filter(R.string.caliber_44_special, "44-special"))
                _calibers.add(Filter(R.string.caliber_44_70, "44-40"))
                _calibers.add(Filter(R.string.caliber_444, "444-marlin"))
                _calibers.add(Filter(R.string.caliber_45_acp, "45acp"))
                _calibers.add(Filter(R.string.caliber_45_acp, ".45-auto-rim"))
                _calibers.add(Filter(R.string.caliber_45_auto, ".45-auto-rim"))
                _calibers.add(Filter(R.string.caliber_45_black, "45-black-powder"))
                _calibers.add(Filter(R.string.caliber_45_colt, "45-colt"))
                _calibers.add(Filter(R.string.caliber_45_gap, "45-gap"))
                _calibers.add(Filter(R.string.caliber_45_long, "45-long-colt"))
                _calibers.add(Filter(R.string.caliber_45_100, "45-100"))
                _calibers.add(Filter(R.string.caliber_45_70, "45-70-government"))
                _calibers.add(Filter(R.string.caliber_45_90, "45-90"))
                _calibers.add(Filter(R.string.caliber_450, "450-bushmaster"))
                _calibers.add(Filter(R.string.caliber_450_marlin, "450-marlin"))
                _calibers.add(Filter(R.string.caliber_450_nitro, "450-nitro-express"))
                _calibers.add(Filter(R.string.caliber_450_rigby, "450-rigby"))
                _calibers.add(Filter(R.string.caliber_450_400, "450-400-nitro-express"))
                _calibers.add(Filter(R.string.caliber_454, "454-casull"))
                _calibers.add(Filter(R.string.caliber_458, "458-lott"))
                _calibers.add(Filter(R.string.caliber_458_socom, "458-socom"))
                _calibers.add(Filter(R.string.caliber_458_winchester, "458-winchester-magnum"))
                _calibers.add(Filter(R.string.caliber_460, "460-sw"))
                _calibers.add(Filter(R.string.caliber_460_weatherby, "460-weatherby-magnum"))
                _calibers.add(Filter(R.string.caliber_470, "470-nitro-express"))
                _calibers.add(Filter(R.string.caliber_475, "475-linebaugh"))
                _calibers.add(Filter(R.string.caliber_480, "480-ruger"))

                _calibers.add(Filter(R.string.caliber_545, "545x39mm"))
                _calibers.add(Filter(R.string.caliber_556, "5-56-45mm-nato"))
                _calibers.add(Filter(R.string.caliber_56, "56x52R"))
                _calibers.add(Filter(R.string.caliber_57, "5.7x28mm"))
                _calibers.add(Filter(R.string.caliber_50, "50-action-express"))
                _calibers.add(Filter(R.string.caliber_50_beowulf, "50-beowulf"))
                _calibers.add(Filter(R.string.caliber_50_black, "50-black powder"))
                _calibers.add(Filter(R.string.caliber_50_BMG, "50-bmg"))
                _calibers.add(Filter(R.string.caliber_500_nitro, "500-nitro-express"))
                _calibers.add(Filter(R.string.caliber_500_sw, "500-sw"))
                _calibers.add(Filter(R.string.caliber_505, "505-gibbs"))
                _calibers.add(Filter(R.string.caliber_52, "52-black-powder"))
                _calibers.add(Filter(R.string.caliber_54, "54-black-powder"))
                _calibers.add(Filter(R.string.caliber_58, "58-black-powder"))

                _calibers.add(Filter(R.string.caliber_6_5, "65-creedmoore"))
                _calibers.add(Filter(R.string.caliber_6_5_grendal, "6-5mm-grendel")) // check
                _calibers.add(Filter(R.string.caliber_6_5_284, "6.5x284"))
                _calibers.add(Filter(R.string.caliber_6_5_jp, "6.5x50"))
                _calibers.add(Filter(R.string.caliber_6_5_carcano, "6.5x52-caracano"))
                _calibers.add(Filter(R.string.caliber_6_5_swedish, "6.5x55-swedish"))
                _calibers.add(Filter(R.string.caliber_6_8_spcii, "68mm-spc"))
                _calibers.add(Filter(R.string.caliber_6mm, "6mm-norma"))
                _calibers.add(Filter(R.string.caliber_6mm_remington, "6mm-remington"))

                _calibers.add(Filter(R.string.caliber_75_swiss, "7.5x55-swiss"))
                _calibers.add(Filter(R.string.caliber_76, "7.62x25-tokarev"))
                _calibers.add(Filter(R.string.caliber_762, "762x39"))
                _calibers.add(Filter(R.string.caliber_762_nato, "7-62x51mm-nato"))
                _calibers.add(Filter(R.string.caliber_762r, "7.62x54R"))
                _calibers.add(Filter(R.string.caliber_765, "7.65mm-argentine"))
                _calibers.add(Filter(R.string.caliber_77, "7.7x58-japanese"))
                _calibers.add(Filter(R.string.caliber_7mm, "7mm-mauser"))
                _calibers.add(Filter(R.string.caliber_7mm_remington, "7mm"))
                _calibers.add(Filter(R.string.caliber_7mm_remington_sa, "7mm-remington-sa-ultra-mag"))
                _calibers.add(Filter(R.string.caliber_7mm_remington_ultra, "7mm-remington-ultra-mag"))
                _calibers.add(Filter(R.string.caliber_7mm_westerner, "7mm-shooting-times-westerner"))
                _calibers.add(Filter(R.string.caliber_7mm_weatherby, "7mm-weatherby-magnum"))
                _calibers.add(Filter(R.string.caliber_7mm_winchester, "7mm-winchester-short-magnum"))
                _calibers.add(Filter(R.string.caliber_7mm_08, "7mm-08-remington"))
                _calibers.add(Filter(R.string.caliber_757, "7x57R"))
                _calibers.add(Filter(R.string.caliber_764, "7x64"))
                _calibers.add(Filter(R.string.caliber_765r, "7x65R"))

                _calibers.add(Filter(R.string.caliber_8mm, "8mm-mauser"))
                _calibers.add(Filter(R.string.caliber_8mm_remington, "8mm-remington-magnum"))
                _calibers.add(Filter(R.string.caliber_856, "8x56mmr"))
                _calibers.add(Filter(R.string.caliber_857, "8x57-jr"))
                _calibers.add(Filter(R.string.caliber_857_mauser, "8x57-js-mauser"))

                _calibers.add(Filter(R.string.caliber_93_62, "9.3x62-mauser"))
                _calibers.add(Filter(R.string.caliber_93_72, "9.3x72R"))
                _calibers.add(Filter(R.string.caliber_93_74, "9.3x74R"))
                _calibers.add(Filter(R.string.caliber_9mm, "9mm-luger"))
                _calibers.add(Filter(R.string.caliber_9mm_makarov, "9mm-makarov"))
                _calibers.add(Filter(R.string.caliber_9_21, "9x21mm"))
                _calibers.add(Filter(R.string.caliber_9_23_winchester, "9x23mm"))
                _calibers.add(Filter(R.string.caliber_9_23, "9-23mm-largo"))
            }
            return _calibers
        }
        set(value) {
            _calibers = value
        }

    private lateinit var _manufacturers : ArrayList<Filter>
    var manufacturers : ArrayList<Filter>
        get() {
            if(!::_manufacturers.isInitialized){

            }
            return _manufacturers
        }
        set(value) {
            _manufacturers = value
        }

    private lateinit var _firearmTypes : ArrayList<Filter>
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