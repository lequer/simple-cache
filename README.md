## Synopsis

This is a simple cache library in java. It is based on ConcurrentHashMap.

## Code Example

Create a cache with String keys and Long values. 
```java
//    public <K extends Object, V extends Object> void create(String name, long timeToLive, long timeInterval, int maxElements, Class<K> keyType, Class<V> valueType) {
CacheManager instance = CacheManager.INSTANCE;
instance.create("testCache", 10, 100, 100, String.class, Long.class);
```
Storing and accessing values
```java
Cache = instance.getCache("testCache");
cache.put("theKey", 10L);
// ...
Long v = cache.get("theKey");
// save the cache to temporary file for later use
cache.write();
```

## Motivation

I created this simple to use cache for projects that do not need to use the existing massive libraries


## Tests

To run the tests, you can run

    ./gradlew test


