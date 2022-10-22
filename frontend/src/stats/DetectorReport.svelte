<script>
    const datas = [
        {
            "score": 30.0,
            "anomaly": "IP address likely to be a TOR exit node!",
            "detector": "VPN Detector",
            "operation": "+"
        },
        {
            "score": 50.0,
            "anomaly": "Different country detected against IP location (from shipping/billing address)",
            "detector": "Address Difference",
            "operation": "+"
        },
        {
            "score": 60.0,
            "anomaly": "User IP address history indicates impossible travel! (User travelled 1460553.9686884005 meters in 1 hour to a different country. Vehicle can either be an airliner or a car.)",
            "detector": "Account Takeover",
            "operation": "+"
        }
    ];
</script>

<div class="list">
    <div class="head">
        <h1 class="title">Detector</h1>
        <h1 class="description">Anomaly</h1>
        <h1 class="score">Score</h1>
    </div>
    {#each datas as data}
        <div class="report">
            <h1 class="title">{data["detector"]}</h1>
            <p class="description">{data["anomaly"]}</p>
            <h1 class="score">{data["score"]}</h1>
        </div>
    {/each}
</div>


<style lang="scss">
    .list {
        display: flex;
        flex-direction: column;
        gap: 1rem;

        width: 100%;
    }

    .head {
        display: grid;
        gap: 1rem;
        grid-template-columns: .5fr 1fr .08fr;
    }

    .report {
        display: grid;
        gap: 1rem;
        grid-template-columns: .5fr 1fr .08fr;

        .score {
            float: right;
        }

        h1 {
            font-size: 1.5rem;
        }

        border: solid 2px red;
        background-color: #8a0c0c9d;

        padding: 1rem;
        border-radius: 10px;
    }
</style>