package com.zdog.demo.ui.status

import com.zdog.demo.ui.shapes.Raindrop
import com.zdog.demo.ui.shapes.Hail
import com.zdog.demo.ui.shapes.Sleet
import com.zdog.demo.ui.shapes.Snowflake

object WeatherStatus {
    private val unknown = { Unknown() }
    private val sunny= { Sunny() }

    private val cloudy= { Cloudy() }
    private val partlyCloudy= { PartlyCloudy() }

    private val fog = { Fog() }
    private val dust = { Dust() }

    private val windy = { Windy() }
    private val tornado = { Tornado() }

    private val drizzle = { Drop(
        Raindrop(),
        Raindrop()
    ) }
    private val freezingDrizzle = { Drop(
        Sleet(),
        Sleet()
    ) }
    private val rain = { Drop(
        Raindrop(),
        Raindrop()
    ) }
    private val rainShowers = { Drop(
        Raindrop(),
        Raindrop(),
        Raindrop(), showers = true) }
    private val heavyRain = { Drop(
        Raindrop(),
        Raindrop(),
        Raindrop(),
        Raindrop()
    ) }
    private val storm = { Storm() }

    private val hail = { Drop(
        Hail(),
        Hail(),
        Hail()
    ) }
    private val sleet = { Drop(
        Sleet(),
        Sleet(),
        Sleet()
    )}

    private val lightSnow = { Drop(
        Snowflake(),
        Snowflake(), showers = true) }
    private val snow = { Drop(
        Snowflake(),
        Snowflake(),
        Snowflake()
    ) }
    private val snowShowers = { Drop(
        Snowflake(),
        Snowflake(),
        Snowflake(), showers = true) }
    private val heavySnow = { Drop(
        Snowflake(),
        Snowflake(),
        Snowflake(),
        Snowflake()
    )  }
    private val blizzard = { Blizzard() }

    private val mixedRainSleet = { Drop(
        Raindrop(),
        Sleet(),
        Raindrop()
    ) }
    private val mixedRainHail = { Drop(
        Raindrop(),
        Hail(),
        Raindrop()
    ) }
    private val mixedRainSnow = { Drop(
        Raindrop(),
        Snowflake(),
        Raindrop()
    ) }
    private val mixedSnowSleet = { Drop(
        Snowflake(),
        Sleet(),
        Snowflake()
    ) }

    private val thunderstorms = { Thunderstorms() }

    private val conditions = arrayOf(
        tornado,              // 0 - Tornado
        storm,                // 1 - Tropical storm
        tornado,              // 2 - Hurricane
        thunderstorms,        // 3 - Severe thunderstorms
        thunderstorms,        // 4 - Thunderstorms
        mixedRainSnow,        // 5 - Mixed rain and snow
        mixedRainSleet,       // 6 - Mixed rain and sleet
        mixedSnowSleet,       // 7 - Mixed snow and sleet
        freezingDrizzle,      // 8 - Freezing drizzle
        drizzle,              // 9 - Drizzle
        sleet,                // 10- Freezing rain
        rainShowers,          // 11- Showers
        rain,                 // 12- Rain
        snowShowers,          // 13- Snow flurries
        lightSnow,            // 14- Light snow showers
        snow,                 // 15- Blowing snow
        snow,                 // 16- Snow
        hail,                 // 17- Hail
        sleet,                // 18- Sleet
        dust,                 // 19- Dust
        fog,                  // 20- Foggy
        fog,                  // 21- Haze
        fog,                  // 22- Smoky
        windy,                // 23- Blustery
        windy,                // 24- Windy
        cloudy,               // 25- Cold
        cloudy,               // 26- Cloudy
        cloudy,               // 27- Mostly cloudy (night)
        cloudy,               // 28- Mostly cloudy (day)
        partlyCloudy,         // 29- Partly cloudy (night)
        partlyCloudy,         // 30- Partly cloudy (day)
        sunny,                // 31- Clear (night)
        sunny,                // 32- Sunny
        sunny,                // 33- Fair (night)
        sunny,                // 34- Fair (day)
        mixedRainHail,        // 35- Mixed rain and hail
        sunny,                // 36- Hot
        thunderstorms,        // 37- Isolated thunderstorms
        thunderstorms,        // 38- Scattered thunderstorms
        rainShowers,          // 39- Scattered showers (day)
        heavyRain,            // 40- Heavy rain
        snowShowers,          // 41- Scattered snow showers (day)
        heavySnow,            // 42- Heavy snow
        blizzard,             // 43- Blizzard
        unknown,              // 44- Not available
        rainShowers,          // 45- Scattered showers (night)
        snowShowers,          // 46- Scattered snow showers (night)
        thunderstorms         // 47- Scattered thundershowers
    )

    fun toStaticDay(code: Int) = condition(code).toStaticDay()
    fun toStaticNight(code: Int) = condition(code).toStaticNight()
    fun toDynamicDay(code: Int) = condition(code).toDynamicDay()
    fun toDynamicNight(code: Int) = condition(code).toDynamicNight()

    private fun condition(code: Int) =
            if (code < 0 || code > 47) conditions[44].invoke() else conditions[code].invoke()
}