<CsoundSynthesizer>
<CsOptions>
-g -odac /Users/abused/Dropbox/public/sandbox/ProcessingLibraries/UPlayLib/examples/c_soundo/data/__CSOUNDO__.csd
</CsOptions>
<CsInstruments>
sr = 44100
kr = 1470
ksmps = 30
nchnls = 1
0dbfs = 1

gi_sin ftgen 1, 0, 8192, 10, 1

instr 1
    ; Keep Csound running
endin

instr 2
    idur = p3
    iamp = p4
    ifreq = p5
    
    k1 line iamp, idur, 0
    a1 oscil k1, ifreq, gi_sin
    
    out a1
endin




</CsInstruments>
<CsScore>
i 1 0 [60 * 60 * 24]

</CsScore>
</CsoundSynthesizer>
