package jp.jaxa.iss.kibo.rpc.defaultapk.core;

import jp.jaxa.iss.kibo.rpc.api.KiboRpcApi;

public class Astro {
    public static Astro bee;
    public KiboRpcApi api;
    private Astro(KiboRpcApi api){
        bee = this;
        this.api = api;
        api.startMission();
    }

    public static void bee(KiboRpcApi api){
        new Astro(api);
    }
}
