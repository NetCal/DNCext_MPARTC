# NetworkCalculus.org DNC

Deterministic Network Calculus is a methodology for worst-case modeling and analysis of communication networks. It enables to derive deterministic bounds on a server’s backlog as well as a flow’s end-to-end delay. Given a directed graph of servers (server graph) and the flows crossing these servers, the Deterministic Network Calculator (DNC) automates the derivation of bounds.

## Usage

These experiments depend on the code restructuring of the DNC v2.5.0.

## MPA RTC Curve Backend

This code allows to use the curve backend implementation of the MPA RTC toolbox. Details can be found in the following publication:  

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
