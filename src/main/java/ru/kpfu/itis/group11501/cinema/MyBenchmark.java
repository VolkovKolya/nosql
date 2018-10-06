/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package ru.kpfu.itis.group11501.cinema;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.kpfu.itis.group11501.cinema.config.PostgreSQLConfig;
import ru.kpfu.itis.group11501.cinema.entity.MovieStatistic;
import ru.kpfu.itis.group11501.cinema.repository.MovieStatisticRepository;
import ru.kpfu.itis.group11501.cinema.repository.postgres.MovieStatisticRepositoryPostgres;

import javax.persistence.EntityManagerFactory;
import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@Warmup(iterations = 10)
public class MyBenchmark {



    @State(Scope.Benchmark)
    public static class MyState {

        @Setup(Level.Trial)
        public void doSetup() {
            JdbcTemplate [] jdbcTemplates = PostgreSQLConfig.getJdbcTemplates();
            movieStatisticRepository = new MovieStatisticRepositoryPostgres(jdbcTemplates);
        }

        @Setup(Level.Invocation)
        public void setupMovie() {
            // to do generate random parametrs;
            movieStatistic =  new MovieStatistic();
        }

        @Setup(Level.Invocation)
        public void setupId() {
            // to do generate random id for movieStatistic;
            id =  new String();
        }

        public MovieStatisticRepository movieStatisticRepository;
        public MovieStatistic movieStatistic;
        public String id;

    }

    @Benchmark
    public void selectMethod(Blackhole blackhole,MyState state) {
        MovieStatistic movieStatistic = state.movieStatisticRepository.getMovieStatisticById(state.id);
        blackhole.consume(movieStatistic);
    }

    @Benchmark
    public void insertMethod(MyState state) {
        state.movieStatisticRepository.addMovieStatistic(state.movieStatistic);
    }



}
