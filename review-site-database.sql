--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner:
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: businesses; Type: TABLE; Schema: public; Owner: Guest; Tablespace:
--

CREATE TABLE businesses (
    id integer NOT NULL,
    name character varying,
    locationid integer
);


ALTER TABLE businesses OWNER TO "Guest";

--
-- Name: businesses_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE businesses_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE businesses_id_seq OWNER TO "Guest";

--
-- Name: businesses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE businesses_id_seq OWNED BY businesses.id;


--
-- Name: locations; Type: TABLE; Schema: public; Owner: Guest; Tablespace:
--

CREATE TABLE locations (
    id integer NOT NULL,
    state character varying,
    city character varying
);


ALTER TABLE locations OWNER TO "Guest";

--
-- Name: locations_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE locations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE locations_id_seq OWNER TO "Guest";

--
-- Name: locations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE locations_id_seq OWNED BY locations.id;


--
-- Name: reviews; Type: TABLE; Schema: public; Owner: Guest; Tablespace:
--

CREATE TABLE reviews (
    id integer NOT NULL,
    businessid integer,
    score integer,
    reviewcomment character varying,
    reviewername character varying,
    reviewtitle character varying,
    locationid integer
);


ALTER TABLE reviews OWNER TO "Guest";

--
-- Name: reviews_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE reviews_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE reviews_id_seq OWNER TO "Guest";

--
-- Name: reviews_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE reviews_id_seq OWNED BY reviews.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY businesses ALTER COLUMN id SET DEFAULT nextval('businesses_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY locations ALTER COLUMN id SET DEFAULT nextval('locations_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY reviews ALTER COLUMN id SET DEFAULT nextval('reviews_id_seq'::regclass);


--
-- Data for Name: businesses; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY businesses (id, name, locationid) FROM stdin;
8	Voodoo Doughnuts	1
9	Some Pizza PLace	4
11	foo	1
\.


--
-- Name: businesses_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('businesses_id_seq', 11, true);


--
-- Data for Name: locations; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY locations (id, state, city) FROM stdin;
4	NY	New York City
1	OR	Portland
6	NC	Raleigh
\.


--
-- Name: locations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('locations_id_seq', 6, true);


--
-- Data for Name: reviews; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY reviews (id, businessid, score, reviewcomment, reviewername, reviewtitle, locationid) FROM stdin;
42	8	4	I like donuts	Yusuf	Tasty!	1
43	8	2	Long line, old donuts	Mr. Complainer	Not so great	1
44	9	5	Yummmmmmmmmmmm. I liked the food.	Mr. Satisfied	Tasty!	4
45	9	4	I'll come back!	Yusuf	Good	4
\.


--
-- Name: reviews_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('reviews_id_seq', 50, true);


--
-- Name: businesses_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace:
--

ALTER TABLE ONLY businesses
    ADD CONSTRAINT businesses_pkey PRIMARY KEY (id);


--
-- Name: locations_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace:
--

ALTER TABLE ONLY locations
    ADD CONSTRAINT locations_pkey PRIMARY KEY (id);


--
-- Name: reviews_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace:
--

ALTER TABLE ONLY reviews
    ADD CONSTRAINT reviews_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--
