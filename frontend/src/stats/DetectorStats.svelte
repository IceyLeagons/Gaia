<script lang="ts">
    import {onMount} from "svelte";
    import {Chart} from "frappe-charts/dist/frappe-charts.min.esm";

    let element;
    export let data;

    onMount(() => {
        let labels = [];
        let scores = [];

        data["applied_rules"].forEach(element => {
            labels.push(element["detector"]);
            scores.push(element["score"]);
        });

        const chartData = {
            labels: labels,
            datasets: [
                {
                    name: "Scores",
                    type: "bar",
                    values: scores,
                }
            ],
        };

        const chart = new Chart(element, {
            // or a DOM element,
            // new Chart() in case of ES6 module with above usage
            title: "Scores",
            data: chartData,
            type: "pie", // or 'bar', 'line', 'scatter', 'pie', 'percentage'
            height: 400,
            colors: ["red"],
        });
    });
</script>

<div bind:this={element} style="color: white !important;"></div>
