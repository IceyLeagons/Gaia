# 🌿 Gaia

### ✅ 48-hour Hackathon submission to the JunctionX event.

🌿 Gaia is a rest API which checks whether a provided user *- from the provided data -* looks suspicious enough to be
considered a fraudster.

## 🤔 Why Gaia

🌿 Gaia, for the most part uses Geolocation. Geolocation could be approximate, or precise. With precise geolocation 🌿
Gaia is able to approximate which *WiFi* network a user is connected to, and whether it is a 🔓 *public network*. 🌿 Gaia
is also able to detect whether a user is connected to a *VPN*.

## 📂 Architecture

🌿 Gaia was designed to be as modular as possible within the constraints of the 48-hour time limit. It uses Spring for
headless server hosting and dependency injection.

## 💪 Detectors

Currently there are 5 different detectors implemented, but their number will only grow as time goes on!
Here's the list of all the ones currently implemented:

- 🙅‍Impossible travel
    - this is triggered if a user travels more than 120kms in a single hour.
- 🔀 Address Difference
    - there are currently 2 different versions of this implemented.
        - 🚢 Current location vs. Shipping/Billing address
            - this checks whether the user is in the same region/country as the provided shipping/billing address.
        - 🛳️ Shipping address vs. Billing address
            - this checks whether the shipping address is in the same country as the billing address.
- 🏠 Shipping address type checking
    - this detector checks if the type of the shipping address is residential housing or not.
- 📅 Bad IP detection
    - of course, there is a standard bad IP detection just for good measure.
- 📶 Public WiFi detection
    - this checks whether the user is connected to a possible Freenet network.
    - this is done with the help of 2 awesome apis, namely Wifimap and Wigle.

## 👷 Compiling and Running

Compilation is as standard as it could be.

    ./gradlew build

Then execute it with:

    ./gradlew bootRun

This will build the frontend automatically, run Spring and host the frontend via Spring as well!

### ⚠️BEFORE YOU RUN

The application requires API keys under `backend/src/main/resources/local.properties`.

Use the following template:
```properties
apis.seon.key =
apis.wigle.key =
apis.google.maps.key =
```

## 🤓 What APIs/tools does Gaia use?

🌿 Gaia can do most of its work thanks to the following APIs:

- 🧭 [geocode.maps.co](https://geocode.maps.co/)
    - and by extension 📖 [OpenStreetMap](https://www.openstreetmap.org/)
- 📊 [SEON](https://seon.io/)
- 🗺️ [Google Maps](https://maps.google.com/) services
- 📶 [Wifimap](https://wifimap.io/)
- 📃 [Wigle](https://wigle.net/)
- 🍃 [Spring](https://spring.io/)
- ♨ [Kotlin](https://kotlinlang.org/)

## 🕸️ Frontend Technologies (major libs)
- 📜 [Svelte](https://svelte.dev/)
- 🍂 [Leaflet](https://leafletjs.com/)
- 📈 [Frappe Charts](https://github.com/himynameisdave/svelte-frappe-charts)
- 📄 [Sass](https://sass-lang.com/)
- 📖 [OpenStreetMap](https://www.openstreetmap.org/)
- ‼ [Sweetalert](https://sweetalert.js.org/)
- 🎨 [Vite](https://vitejs.dev/)

🤗 A huge thank you for all of these APIs for making the development of 🌿 Gaia a bunch more relaxing, and for providing *
HUGE datasets* for no cost!