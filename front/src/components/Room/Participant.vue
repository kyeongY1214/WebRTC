<template>
  <div>
    <video
      ref="video"
      autoplay
      :width="videoWidth"
      :height="[isMobile ? '' : videoHeight]"
    ></video>
  </div>
</template>

<script>
import isMobile from "@/mixin/isMobile.js";

export default {
  name: "Participant",
  mixins: [isMobile],
  data() {
    return {
      rtcPeer: null,
    };
  },
  props: {
    userId: {
      type: Number,
    },
    ws: {
      type: WebSocket,
    },
    videoHeight: {
      type: Number,
    },
    videoWidth: {
      type: Number,
    },
  },

  methods: {
    offerToReceiveVideo(error, offerSdp, wp) {
      if (error) return console.error("sdp offer error");
      // console.log("Invoking SDP offer callback function");
      let message = {
        id: "receiveVideoFrom",
        sender: this.userId,
        sdpOffer: offerSdp,
      };
      this.sendMessage(message);
    },
    onIceCandidate(candidate, wp) {
      // console.log("Local candidate" + JSON.stringify(candidate));

      let message = {
        id: "onIceCandidate",
        candidate: candidate,
        name: this.userId,
      };
      this.sendMessage(message);
    },
    dispose() {
      // console.log("Disposing participant " + this.userId);
      this.rtcPeer.dispose();
    },
    getVideoElement() {
      return this.$refs.video;
    },
    sendMessage(message) {
      if (this.ws.readyState !== this.ws.OPEN) {
        // console.log("[errMessage] Skip, WebSocket session isn't open");
        // console.log(this.ws);
        return;
      }
      const jsonMessage = JSON.stringify(message);
      // console.log("[sendMessage] message: " + jsonMessage);
      this.ws.send(jsonMessage);
    },
  },

  created() {
    Object.defineProperty(this, "rtcPeer", { writable: true });
  },
};
</script>

<style>
</style>