<script>
    import Input from "./Input.svelte";
    import DetectorReport from "./stats/DetectorReport.svelte";
    import DetectorStats from "./stats/DetectorStats.svelte";
    import Map from "./stats/Map.svelte";
    import swal from "sweetalert";

    let data = undefined;
    let coords = undefined;

    function setData(newData) {
        if (!newData["applied_rules"]) {
            swal("No fraud!", "The input data is completely clean!", "success");
            data = undefined;
            coords = undefined;
            return;
        }

        swal("Oh no!", "We've detected some fraud possibilities!", "error");
        data = newData;
        coords = data["coords"];
    }
</script>

<main class="main">
    <div class="row center-xs">
        <div class="col-xs-6">
            <h1 class="title">Gaia</h1>
        </div>
    </div>
    <div class="row center-xs">
        <div class="col-xs-12 col-md-6">
            <Input {setData}/>
        </div>
    </div>
    {#if data}
        <div class="row">
            <div class="col-md-5 col-sx-12">
                <div class="card">
                    <DetectorReport raw={data}/>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card meter-card">
                    <h1 style="text-align: center;">Fraud Score: {data["total_score"]}</h1>
                    <div class="labels" style="text-align: center;">
                        <h3>0%</h3>
                        <h3>50%</h3>
                        <h3>100% (x)</h3>
                    </div>
                    <meter value={data["total_score"]} min="0" low="40" high="95" max="100" optimum="0"/>
                </div>
            </div>
            <div class="col-md-4 col-xs-12">
                <div class="card">
                    <DetectorStats {data}/>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-xs-12">
                <div class="card">
                    <Map coords={coords}/>
                </div>
            </div>
        </div>
    {/if}
</main>

<style lang="scss">
  .title {
    font-size: 3rem;
  }

  .main {
    margin: 1rem;
  }

  .row {
    margin-top: 1rem;
  }

  .meter-card {
    display: flex;
    flex-direction: column;
    gap: 1rem;
  }

  .meter-card .labels {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }

  /*meter */
  meter::-webkit-meter-bar {
    background: #e6e6e9;
  }

  /*background color of bar*/
  meter::-webkit-meter-optimum-value {
    background: green;
  }

  meter::-webkit-meter-suboptimum-value {
    background: orange;
  }

  meter::-webkit-meter-even-less-good-value {
    background: red;
  }

  meter {
    margin-top: -1.2rem;
    height: 3rem;
    width: 100%;
  }
</style>
