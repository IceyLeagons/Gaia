<script>
  import * as L from "leaflet";
  import { onMount } from "svelte";

  let mapContainer;
  function createMap(container) {
    let map = L.map(container).setView([50, 10], 3.3);
    L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
      maxZoom: 14,
      attribution:
        '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>',
    }).addTo(map);


    var circle = L.circle([30, 50], {
      color: "red",
      fillColor: "#f03",
      fillOpacity: 0.5,
      radius: 2000000,
    }).addTo(map);

    var marker = L.marker([51.513, -0.09], {
      title: "Billing address",
    }).addTo(map);
    marker.bindPopup("Billing address");

    var marker2 = L.marker([30.513, -5.09], {
      title: "Shipping address",
    }).addTo(map);
    marker2.bindPopup("Shipping address");
  }

  onMount(() => {
    createMap(mapContainer);
  });
</script>

<svelte:head>
  <!-- In the REPL you need to do this. In a normal Svelte app, use a CSS Rollup plugin and import it from the leaflet package. -->
  <link
    rel="stylesheet"
    href="https://unpkg.com/leaflet@1.6.0/dist/leaflet.css"
    integrity="sha512-xwE/Az9zrjBIphAcBb3F6JVqxf46+CDLwfLMHloNu6KEQCAWi6HcDUbeOfBIptF7tcCzusKFjFw2yuvEpDL9wQ=="
    crossorigin=""
  />
</svelte:head>

<div id="container" style="height:400px;width:100%" bind:this={mapContainer} />
