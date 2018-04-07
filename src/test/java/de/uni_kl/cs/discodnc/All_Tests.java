/*
 * This file is part of the Disco Deterministic Network Calculator.
 *
 * Copyright (C) 2013 - 2018 Steffen Bondorf
 * Copyright (C) 2017+ The DiscoDNC contributors
 *
 * Distributed Computer Systems (DISCO) Lab
 * University of Kaiserslautern, Germany
 *
 * http://discodnc.cs.uni-kl.de
 *
 *
 * The Disco Deterministic Network Calculator (DiscoDNC) is free software;
 * you can redistribute it and/or modify it under the terms of the 
 * GNU Lesser General Public License as published by the Free Software Foundation; 
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */

package de.uni_kl.cs.discodnc;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.junit.platform.suite.api.SelectClasses;

@ExtendWith(Extension.class)
@SelectClasses({ S_1SC_1F_1AC_Test.class, S_1SC_2F_1AC_Test.class, S_1SC_2F_2AC_Test.class, S_1SC_10F_10AC_Test.class,
		TA_2S_1SC_1F_1AC_1P_Test.class, TA_3S_1SC_2F_1AC_1P_Test.class, TA_2S_1SC_2F_1AC_1P_Test.class,
		TA_4S_1SC_2F_1AC_2P_Test.class, TA_2S_1SC_2F_1AC_2P_Test.class, TA_3S_1SC_3F_1AC_3P_Test.class,
		TA_2S_1SC_4F_1AC_1P_Test.class, TA_2S_2SC_1F_1AC_1P_Test.class, TA_2S_2SC_2F_1AC_1P_Test.class,
		TR_3S_1SC_2F_1AC_2P_Test.class, TR_7S_1SC_3F_1AC_3P_Test.class, FF_3S_1SC_2F_1AC_2P_Test.class,
		FF_4S_1SC_3F_1AC_3P_Test.class, FF_4S_1SC_4F_1AC_4P_Test.class })

public class All_Tests {
}
