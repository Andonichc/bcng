package com.andonichc.bcng.domain

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


class AppSchedulers(val main: Scheduler,
                    val io: Scheduler = Schedulers.io(),
                    val computation: Scheduler = Schedulers.computation(),
                    val trampoline: Scheduler = Schedulers.trampoline())