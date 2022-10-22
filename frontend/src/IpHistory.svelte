<script>
    export let close;
    export let addMore;
    export let history;
</script>
<div class="list">
    <h1>IP History</h1>
    <div class="ip" style="text-align: center;">
        <h1>Time</h1>
        <h1>IP</h1>
    </div>
    <div class="overflow">
        {#each history as _, index}
            <div class="ip">
                <input type="datetime-local" on:change={(e) => {
                history[index].key = new Date(e.target["value"]).getTime();
            }}/>
                <input type="text" on:change={(e) => {
                history[index].value = e.target["value"];
            }}/>
            </div>
        {/each}
    </div>

    <span class="btn" on:click={addMore}>+ More +</span>
    <span class="btn" on:click={close}>Close</span>
</div>


<style lang="scss">
  .list {
    display: flex;
    flex-direction: column;
    gap: 1rem;

    .ip {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 2rem;
    }
  }

  input {
    border: none;
    color: white;
    padding: 1rem;
    background-color: #323233;
  }

  .overflow {
    display: flex;
    flex-direction: column;
    gap: 1rem;

    overflow-y: auto;
    max-height: 40%;
  }

  .btn {
    cursor: pointer;

    background-color: #111111;
    padding: 1rem;

    &:hover {
      background-color: #1b1b1b;
    }
  }

</style>