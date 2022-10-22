<script lang="ts">
    import Input from "./Input.svelte";
    import DetectorReport from "./stats/DetectorReport.svelte";
    import DetectorStats from "./stats/DetectorStats.svelte";
    import Map from "./stats/Map.svelte";
    import swal from "sweetalert";

    let data: any = undefined;
    let coords: any = undefined;

    function setData(newData: any) {
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
                    <h1 style="text-align: center;">Fraud Score: {data["total_score"].toFixed(2)}%</h1>
                    <div class="labels">
                        <h3 style="text-align: center;">0%</h3>
                        <h3 style="text-align: center;">50%</h3>
                        <h3 style="text-align: center;">100% (x)</h3>
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

<a href="https://www.iceyleagons.net/"><img
        alt="IceyLeagons logo"
        class="brand" src="https://avatars.githubusercontent.com/u/56756578?s=400&u=6397fa46857dc4064f59a45d95f06a2aeae50bb1&v=4"/></a>
<footer class="footer">
    <small>
        Copyright &copy; 2022 IceyLeagons | All rights reserved.<br>
        Used libraries can be found on the project's <a href="https://github.com/IceyLeagons/Gaia">GitHub page</a>
    </small>
</footer>

<style lang="scss">
  .footer {
    text-align: center;
    width: 100%;

    margin-bottom: 1rem;
  }

  .brand {
    position: fixed;
    top: 1rem;
    right: 1rem;

    width: 40px;
    aspect-ratio: 1;
    transition: .3s;
    cursor: pointer;

    &:hover {
      transform: translateY(-3px);
    }
  }

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

  meter {
    margin-top: -1.2rem;
    height: 3rem;
    width: 100%;

    &::-webkit-meter-bar {
      background: #e6e6e9;
    }

    &::-webkit-meter-optimum-value {
      background: green;
    }

    &::-webkit-meter-suboptimum-value {
      background: orange;
    }

    &::-webkit-meter-even-less-good-value {
      background: red;
    }
  }
</style>
