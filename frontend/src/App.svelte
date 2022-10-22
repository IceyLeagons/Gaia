<script>
  import Input from "./Input.svelte";
import DetectorReport from "./stats/DetectorReport.svelte";
import DetectorStats from "./stats/DetectorStats.svelte";
  import Map from "./stats/Map.svelte";

</script>
<main class="main">
  <div class="row center-xs">
    <div class="col-xs-6">
      <h1 class="title">Gaida</h1>
    </div>
  </div>
  <div class="row center-xs">
    <div class="col-xs-6">
      <Input />
    </div>
  </div>
  <div class="row">
    <div class="col-xs-6">
      <div class="card">
        <DetectorReport />
      </div>
    </div>
    <div class="col-xs-6">
      <div class="card">
        <DetectorStats />
      </div>
    </div>
  </div>
  
  <div class="row">
    <div class="col-xs-6">
      <div class="card">
        <Map />
      </div>
    </div>
    <div class="col-xs-6">
      <div class="card">
        <DetectorStats />
      </div>
    </div>
  </div>
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
</style>