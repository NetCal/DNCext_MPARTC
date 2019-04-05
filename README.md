# NetworkCalculus.org DNC

Deterministic Network Calculus is a methodology for worst-case modeling and analysis of communication networks. It enables to derive deterministic bounds on a server’s backlog as well as a flow’s end-to-end delay. Given a directed graph of servers (server graph) and the flows crossing these servers, the Deterministic Network Calculator (DNC) automates the derivation of bounds.

### NetCal DNC Requirements

This extension depends on the code restructuring of v2.5.0.

## About the MPA RTC Extension

Real-Time Calculus (RTC) is a branch of Deterministic Network Calculus.
For its analysis, RTC uses the same curve definitions, min-plus-algebraic operations and bounding operations as Deterministic Network Calculus.
For system modeling, RTC focusses more on components, making it a Modular Performance Analysis (MPA).
The [MPA RTC toolbox](https://www.mpa.ethz.ch/Rtctoolbox) consists of implementations for both parts: 
* the former (curve and algebra backend) is written in Java and its bytecode can be downloaded free of charge, 
* the latter (modeling framework) is provided by an open-source Matlab integration.

The modeling-part of the MPA RTC depends on the manual creation of a component-based model.
Connections created between components are mapped to the order of operations to be executed.
I.e., there is no automated derivation of bounds according according to an established analysis (e.g., SFA, PMOOA, TMA) as found in the NetCal DNC.

Vice versa, the component models used in the MPA RTC commonly consist of more complex curves than those used in the NetCal DNC (cf. [experiments](https://github.com/NetCal/DNC_experiments) and [functional tests](https://github.com/NetCal/DNC_func_tests)).
Therefore, the min-plus operations and the bounding operations of the MPA RTC are more powerful, too.

Some of the NetCal DNC analyses (foremost SFA) can in theory be applied to any shape of wide-sense increasing curves.
Therefore, this NetCal project provides wrappers to make the MPA RTC curve and algebra backend compliant with the NetCal DNC interfaces.
As a result, the MPA RTC can act be used as a backend in the NetCal DNC's automated derivation of bounds.

### Curve Backend
First, the MPA RTC's curve class was integrated into the NetCal DNC.
Technical details (based on the [DiscoDNC v2.4](https://github.com/NetCal/DNC/tree/v2.4)) can be found in the following publication:

```plain
@inproceedings{DiscoDNCv2:RTC,
  author    = {Philipp Schon and Steffen Bondorf},
  title     = {Towards Unified Tool Support for Real-time Calculus \& Deterministic Network Calculus},
  booktitle = {Proc. of the Euromicro Conference on Real-Time Systems, Work-in-Progress Session},
  series    = {ECRTS '17},
  month     = {June},
  year      = 2017
}
```

### Min-Plus Operations
This integration is available since [NetCal DNC commit 3383fcd](https://github.com/NetCal/DNC/commit/3383fcd46e35c18ef3b6812f14273f95d605c71c).
Meanwhile it has been added to the functional tests.

### Bounding Operations
**TODO** See [issue #15](https://github.com/NetCal/DNCext_MPARTC/issues/15).
