<script>
    import * as L from "leaflet";
    import {onMount} from "svelte";

    export let coords;

    let mapContainer;

    function createMap(container) {
        let map = L.map(container).setView([50, 10], 3.3);
        L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
            maxZoom: 14,
            attribution:
                '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
        }).addTo(map);


        /*
        var circle = L.circle([30, 50], {
          color: "red",
          fillColor: "#f03",
          fillOpacity: 0.5,
          radius: 2000000,
        }).addTo(map);
        */

        var shippingIcon = L.icon({
            iconUrl: "https://cdn.onlinewebfonts.com/svg/img_190973.png",
            iconSize: [25, 25],
            iconAnchor: [25, 25]
        });
        var billingIcon = L.icon({
            iconUrl: "https://cdn.onlinewebfonts.com/svg/img_342978.png",
            iconSize: [25, 25],
            iconAnchor: [25, 25]
        });

        console.log(coords);
        if (coords["billing_address"]) {
            let billing = coords["billing_address"];
            var marker = L.marker([billing["lat"], billing["lng"]], {
                title: "Billing address",
                icon: billingIcon
            }).addTo(map);
            marker.bindPopup("Billing address");
        }

        if (coords["shipping_address"]) {
            let shipping = coords["shipping_address"];
            var marker = L.marker([shipping["lat"], shipping["lng"]], {
                title: "Shipping address",
                icon: shippingIcon
            }).addTo(map);
            marker.bindPopup("Shipping address");
        }
    }

    onMount(() => {
        createMap(mapContainer);
    });
</script>

<svelte:head>
    <!-- In the REPL you need to do this. In a normal Svelte app, use a CSS Rollup plugin and import it from the leaflet package. -->
    <link
            crossorigin=""
            href="https://unpkg.com/leaflet@1.6.0/dist/leaflet.css"
            integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ=="
            rel="stylesheet"
    />
</svelte:head>

<div bind:this={mapContainer} id="container" style="height:400px;width:100%"/>
