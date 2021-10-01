package org.payten.hazelcastexample.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ConfigurationProperties(prefix = "hazelcast")
@Component
public class HazelcastProperties {
    private List<String> members = new ArrayList<>();

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HazelcastProperties that = (HazelcastProperties) o;
        return Objects.equals(members, that.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(members);
    }

    @Override
    public String toString() {
        return "HazelcastProperties{" +
                "members=" + members +
                '}';
    }
}
