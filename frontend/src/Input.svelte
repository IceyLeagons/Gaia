<script lang="ts">
  import IpHistory from "./IpHistory.svelte";

  let history = [];

  function addMore() {
        history = [...history, {key: "", value: ""}];
  }

  let opened = false;
  function close() {
    opened = false;
  }
</script>

<div class="container card">
  <div class="form">
    <div class="fields fields--1">
      <label class="field">
        <span class="field__label">IP Address</span>
        <input class="field__input text-center" type="text" />
      </label>
    </div>

    <h3 class="separator">Optional Information:</h3>
    <div class="fields fields--2">
      <label class="field">
        <span class="field__label">Shipping Address</span>
        <input class="field__input" type="text" />
      </label>
      <label class="field">
        <span class="field__label">Billing Address</span>
        <input class="field__input" type="text" />
      </label>
    </div>
    <div class="fields fields--2">
      <label class="field">
        <span class="field__label">GPS Latitude</span>
        <input class="field__input" type="number" />
      </label>
      <label class="field">
        <span class="field__label">GPS Longitude</span>
        <input class="field__input" type="number" />
      </label>
    </div>

    <button class="button outlined" style="width: 50%; margin-inline: auto;" on:click={() => {
      opened = true;
    }}>Add IP Address History</button>
  </div>
  <hr />
  <button class="button">Check for fraud</button>
  <hr />
</div>

{#if opened}
  <div class="popup">
    <div class="container">
      <IpHistory close={close} history={history} addMore={addMore} />
    </div>
  </div>
{/if}

<style lang="scss">
  .popup::before {
    content: ' ';
    position: fixed;
    z-index: -1;
    width: 100%;
    height: 100%;

    background-color: rgba(0, 0, 0, 0.8);
  }

  .popup {
    position: fixed;
    left: 0;
    top: 0;

    width: 100%;
    height: 100%;
    z-index: 999;

    display: grid;
    place-items: center;

    .container {
      display: flex;
      justify-content: center;
      background-color: #1e1e1e;

      height: 50%;
    }
  }

  hr {
    height: 1px;
    width: 100%;
    border: 0;
    margin: 1rem 0;
  }

  .container {
    border-radius: 10px;
    max-width: 40rem;
    padding: 1rem 2rem 0;
    margin: 0 auto;
  }

  .form {
    display: grid;
    grid-gap: 1rem;
  }

  .text-center {
    text-align: center;
  }

  .field {
    width: 100%;
    display: flex;
    flex-direction: column;
    border: 1px solid #323233;
    padding: 0.5rem;
    border-radius: 0.25rem;
  }

  .field__label {
    color: white;
    font-size: 0.6rem;
    font-weight: 300;
    text-transform: uppercase;
    margin-bottom: 0.25rem;
  }

  .field__input {
    padding: 0;
    margin: 0;
    border: 0;
    outline: 0;
    font-weight: bold;
    font-size: 1rem;
    width: 100%;
    -webkit-appearance: none;
    appearance: none;
    background-color: transparent;
  }
  .field:focus-within {
    border-color: white;
  }

  .fields {
    display: grid;
    grid-gap: 1rem;
  }
  .fields--2 {
    grid-template-columns: 1fr 1fr;
  }
  .fields--3 {
    grid-template-columns: 1fr 1fr 1fr;
  }

  input {
    color: white;
  }

  .button {
    text-transform: uppercase;
    font-size: 0.8rem;
    font-weight: 600;
    display: block;
    color: black;
    width: 100%;
    padding: 1rem;
    border-radius: 0.25rem;
    border: 0;
    cursor: pointer;
    outline: 0;
    transition: .3s;

    &:hover {
        transform: translateY(-3px);
    }

    &.outlined {
      color: white;
      background-color: #1e1e1e;
      border: solid 1px #ababab;

      &:hover {
        background-color: #2b2929;
      }
    }
  }
</style>
