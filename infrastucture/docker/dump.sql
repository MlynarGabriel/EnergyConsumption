--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5
-- Dumped by pg_dump version 17.5

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: energy_usage_hour; Type: TABLE; Schema: public; Owner: disysuser
--

CREATE TABLE public.energy_usage_hour (
    id bigint NOT NULL,
    community_depleted double precision,
    community_produced double precision,
    community_used double precision,
    grid_portion double precision,
    grid_used double precision,
    hour timestamp(6) without time zone
);


ALTER TABLE public.energy_usage_hour OWNER TO disysuser;

--
-- Name: energy_usage_hour_id_seq; Type: SEQUENCE; Schema: public; Owner: disysuser
--

ALTER TABLE public.energy_usage_hour ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.energy_usage_hour_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: energy_usage_hour_seq; Type: SEQUENCE; Schema: public; Owner: disysuser
--

CREATE SEQUENCE public.energy_usage_hour_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.energy_usage_hour_seq OWNER TO disysuser;

--
-- Data for Name: energy_usage_hour; Type: TABLE DATA; Schema: public; Owner: disysuser
--

COPY public.energy_usage_hour (id, community_depleted, community_produced, community_used, grid_portion, grid_used, hour) FROM stdin;
1	6.735234468587636	1.7885565002749122	0.1204634738966806	31.225547239729384	0.054693825161131054	2025-06-23 17:00:00
\.


--
-- Name: energy_usage_hour_id_seq; Type: SEQUENCE SET; Schema: public; Owner: disysuser
--

SELECT pg_catalog.setval('public.energy_usage_hour_id_seq', 1, true);


--
-- Name: energy_usage_hour_seq; Type: SEQUENCE SET; Schema: public; Owner: disysuser
--

SELECT pg_catalog.setval('public.energy_usage_hour_seq', 1, false);


--
-- Name: energy_usage_hour energy_usage_hour_pkey; Type: CONSTRAINT; Schema: public; Owner: disysuser
--

ALTER TABLE ONLY public.energy_usage_hour
    ADD CONSTRAINT energy_usage_hour_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

