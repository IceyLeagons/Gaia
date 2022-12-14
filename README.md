# πΏ Gaia

## ππ₯³ππ Grand Winner of the 2022 Budapest JunctionX event 

Yes, we have won the hackathon. It's impossible to imagine, but we've done it. Congratulations to all the other submissions, every project was amazing!
We'd like to thank to organizers as well, truly flawless coordination/management.

### β 48-hour Hackathon submission to the JunctionX event.

πΏ Gaia is a rest API which checks whether a provided user *- from the provided data -* looks suspicious enough to be
considered a fraudster.

## π€ Why Gaia

πΏ Gaia, for the most part uses Geolocation. Geolocation could be approximate, or precise. With precise geolocation πΏ
Gaia is able to approximate which *WiFi* network a user is connected to, and whether it is a π *public network*. πΏ Gaia
is also able to detect whether a user is connected to a *VPN*.

## π Architecture

πΏ Gaia was designed to be as modular as possible within the constraints of the 48-hour time limit. It uses Spring for
headless server hosting and dependency injection.

## πͺ Detectors

Currently there are 5 different detectors implemented, but their number will only grow as time goes on!
Here's the list of all the ones currently implemented:

- πβImpossible travel
    - we approximate, whether the user is travelling by plane or car. Then based on this information we select the appropriate threshold.
    - the detector is triggered if a user travels more than the threshold in a single hour.
- π Address Difference
    - there are currently 2 different versions of this implemented.
        - π’ Current location vs. Shipping/Billing address
            - this checks whether the user is in the same region/country as the provided shipping/billing address.
        - π³οΈ Shipping address vs. Billing address
            - this checks whether the shipping address is in the same country as the billing address.
- π  Shipping address type checking
    - this detector checks if the type of the shipping address is residential housing or not.
- π Bad IP detection
    - of course, there is a standard bad IP detection just for good measure.
- πΆ Public WiFi detection
    - this checks whether the user is connected to a possible Freenet network.
    - this is done with the help of 2 awesome apis, namely Wifimap and Wigle.

## π· Compiling and Running

Compilation is as standard as it could be.

    ./gradlew build

Then execute it with:

    ./gradlew bootRun

This will build the frontend automatically, run Spring and host the frontend via Spring as well!

### β οΈBEFORE YOU RUN

The application requires API keys under `backend/src/main/resources/local.properties`.

Use the following template:
```properties
apis.seon.key =
apis.wigle.key =
apis.google.maps.key =
```

## π€ What APIs/tools does Gaia use?

πΏ Gaia can do most of its work thanks to the following APIs:

- π§­ [geocode.maps.co](https://geocode.maps.co/)
    - and by extension π [OpenStreetMap](https://www.openstreetmap.org/)
- π [SEON](https://seon.io/)
- πΊοΈ [Google Maps](https://maps.google.com/) services
- πΆ [Wifimap](https://wifimap.io/)
- π [Wigle](https://wigle.net/)
- π [Spring](https://spring.io/)
- β¨ [Kotlin](https://kotlinlang.org/)

## πΈοΈ Frontend Technologies (major libs)
- π [Svelte](https://svelte.dev/)
- π [Leaflet](https://leafletjs.com/)
- π [Frappe Charts](https://github.com/himynameisdave/svelte-frappe-charts)
- π [Sass](https://sass-lang.com/)
- π [OpenStreetMap](https://www.openstreetmap.org/)
- βΌ [Sweetalert](https://sweetalert.js.org/)
- π¨ [Vite](https://vitejs.dev/)

π€ A huge thank you for all of these APIs for making the development of πΏ Gaia a bunch more relaxing, and for providing *
HUGE datasets* for no cost!
