# DiscoDNC

Deterministic Network Calculus (DNC) is a methodology for worst-case modeling and analysis of communication networks. It enables to derive deterministic bounds on a server’s backlog as well as a flow’s end-to-end delay. Given a directed graph of servers (server graph) and the flows crossing these servers, the Disco Deterministic Network Calculator (DiscoDNC) automates the derivation of bounds.

## Usage

This extension depends on the code restructuring of the DiscoDNC's v2.5 branch (version as of 2018-Aug-06).
Please see https://github.com/NetCal/DiscoDNC/blob/v2.5/README.md for more information.


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
