package com.example.courseselectionbackend.querydsl;

import com.querydsl.core.Tuple;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NamedTuple {
	private Map<String, Object> map;

	public interface Operator {
		public void act(NamedTuple tuple);
	}

	NamedTuple(List<String> names, Tuple tuple) {
		map = listToMap(names, Arrays.asList(tuple.toArray()));
	}

	NamedTuple(List<String> names, Tuple tuple, Operator op) {
		map = listToMap(names, Arrays.asList(tuple.toArray()));
		op.act(this);
	}


	private void add(List<String> names, List<Object> objs) {
		map.putAll(listToMap(names, objs));
	}

	public void add(String name, Object obj) {
		map.put(name, obj);
	}

	public void remove(String name) {
		map.remove(name);
	}

	public void replace(String name, Object obj) {
		map.replace(name, obj);
	}

	private static Map<String, Object> listToMap(List<String> names, List<Object> objs) {
		assert names.size() == objs.size();
		return IntStream.range(0, names.size()).boxed().collect(Collectors.toMap(names::get, objs::get));
	}

	Object getObj(String name) {
		return map.get(name);
	}

	private Map<String, Object> getMap() {
		return map;
	}

	public static List<Map<String, Object>> toMapList(List<Tuple> tuples, List<String> names) {
		return tuples.stream().map(x -> {
			NamedTuple tuple = new NamedTuple(names, x);
			return tuple.getMap();
		}).collect(Collectors.toList());
	}

	public static List<Map<String, Object>> toMapList(List<Tuple> tuples, List<String> names, NamedTuple.Operator op) {
		return tuples.stream().map(x -> {
			NamedTuple tuple = new NamedTuple(names, x, op);
			return tuple.getMap();
		}).collect(Collectors.toList());
	}
}
