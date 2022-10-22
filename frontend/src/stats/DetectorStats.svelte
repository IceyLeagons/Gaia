<script lang="ts">
  import { onMount } from "svelte";
  import { Chart } from "frappe-charts/dist/frappe-charts.min.esm";

  let element;

  onMount(() => {
    const data = {
      labels: [
        "VPN Detector",
        "Address Difference",
        "Account Takeover (Impossible Travel)",
      ],
      datasets: [
        {
          name: "Scores",
          type: "bar",
          values: [30, 50, 60, 40, 22, 3],
        }
      ],
    };

    const chart = new Chart(element, {
      // or a DOM element,
      // new Chart() in case of ES6 module with above usage
      title: "Scores",
      data: data,
      type: "pie", // or 'bar', 'line', 'scatter', 'pie', 'percentage'
      height: 400,
      colors: ["red"],
    });
  });
</script>

<div bind:this={element} style="color: white !important;"></div>
