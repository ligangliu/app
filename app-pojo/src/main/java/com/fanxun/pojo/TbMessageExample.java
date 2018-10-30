package com.fanxun.pojo;

import java.util.ArrayList;
import java.util.List;

public class TbMessageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TbMessageExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andMessageUuidIsNull() {
            addCriterion("message_uuid is null");
            return (Criteria) this;
        }

        public Criteria andMessageUuidIsNotNull() {
            addCriterion("message_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andMessageUuidEqualTo(String value) {
            addCriterion("message_uuid =", value, "messageUuid");
            return (Criteria) this;
        }

        public Criteria andMessageUuidNotEqualTo(String value) {
            addCriterion("message_uuid <>", value, "messageUuid");
            return (Criteria) this;
        }

        public Criteria andMessageUuidGreaterThan(String value) {
            addCriterion("message_uuid >", value, "messageUuid");
            return (Criteria) this;
        }

        public Criteria andMessageUuidGreaterThanOrEqualTo(String value) {
            addCriterion("message_uuid >=", value, "messageUuid");
            return (Criteria) this;
        }

        public Criteria andMessageUuidLessThan(String value) {
            addCriterion("message_uuid <", value, "messageUuid");
            return (Criteria) this;
        }

        public Criteria andMessageUuidLessThanOrEqualTo(String value) {
            addCriterion("message_uuid <=", value, "messageUuid");
            return (Criteria) this;
        }

        public Criteria andMessageUuidLike(String value) {
            addCriterion("message_uuid like", value, "messageUuid");
            return (Criteria) this;
        }

        public Criteria andMessageUuidNotLike(String value) {
            addCriterion("message_uuid not like", value, "messageUuid");
            return (Criteria) this;
        }

        public Criteria andMessageUuidIn(List<String> values) {
            addCriterion("message_uuid in", values, "messageUuid");
            return (Criteria) this;
        }

        public Criteria andMessageUuidNotIn(List<String> values) {
            addCriterion("message_uuid not in", values, "messageUuid");
            return (Criteria) this;
        }

        public Criteria andMessageUuidBetween(String value1, String value2) {
            addCriterion("message_uuid between", value1, value2, "messageUuid");
            return (Criteria) this;
        }

        public Criteria andMessageUuidNotBetween(String value1, String value2) {
            addCriterion("message_uuid not between", value1, value2, "messageUuid");
            return (Criteria) this;
        }

        public Criteria andMessageTypeIsNull() {
            addCriterion("message_type is null");
            return (Criteria) this;
        }

        public Criteria andMessageTypeIsNotNull() {
            addCriterion("message_type is not null");
            return (Criteria) this;
        }

        public Criteria andMessageTypeEqualTo(Integer value) {
            addCriterion("message_type =", value, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeNotEqualTo(Integer value) {
            addCriterion("message_type <>", value, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeGreaterThan(Integer value) {
            addCriterion("message_type >", value, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("message_type >=", value, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeLessThan(Integer value) {
            addCriterion("message_type <", value, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeLessThanOrEqualTo(Integer value) {
            addCriterion("message_type <=", value, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeIn(List<Integer> values) {
            addCriterion("message_type in", values, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeNotIn(List<Integer> values) {
            addCriterion("message_type not in", values, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeBetween(Integer value1, Integer value2) {
            addCriterion("message_type between", value1, value2, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("message_type not between", value1, value2, "messageType");
            return (Criteria) this;
        }

        public Criteria andMessageThemaIsNull() {
            addCriterion("message_thema is null");
            return (Criteria) this;
        }

        public Criteria andMessageThemaIsNotNull() {
            addCriterion("message_thema is not null");
            return (Criteria) this;
        }

        public Criteria andMessageThemaEqualTo(String value) {
            addCriterion("message_thema =", value, "messageThema");
            return (Criteria) this;
        }

        public Criteria andMessageThemaNotEqualTo(String value) {
            addCriterion("message_thema <>", value, "messageThema");
            return (Criteria) this;
        }

        public Criteria andMessageThemaGreaterThan(String value) {
            addCriterion("message_thema >", value, "messageThema");
            return (Criteria) this;
        }

        public Criteria andMessageThemaGreaterThanOrEqualTo(String value) {
            addCriterion("message_thema >=", value, "messageThema");
            return (Criteria) this;
        }

        public Criteria andMessageThemaLessThan(String value) {
            addCriterion("message_thema <", value, "messageThema");
            return (Criteria) this;
        }

        public Criteria andMessageThemaLessThanOrEqualTo(String value) {
            addCriterion("message_thema <=", value, "messageThema");
            return (Criteria) this;
        }

        public Criteria andMessageThemaLike(String value) {
            addCriterion("message_thema like", value, "messageThema");
            return (Criteria) this;
        }

        public Criteria andMessageThemaNotLike(String value) {
            addCriterion("message_thema not like", value, "messageThema");
            return (Criteria) this;
        }

        public Criteria andMessageThemaIn(List<String> values) {
            addCriterion("message_thema in", values, "messageThema");
            return (Criteria) this;
        }

        public Criteria andMessageThemaNotIn(List<String> values) {
            addCriterion("message_thema not in", values, "messageThema");
            return (Criteria) this;
        }

        public Criteria andMessageThemaBetween(String value1, String value2) {
            addCriterion("message_thema between", value1, value2, "messageThema");
            return (Criteria) this;
        }

        public Criteria andMessageThemaNotBetween(String value1, String value2) {
            addCriterion("message_thema not between", value1, value2, "messageThema");
            return (Criteria) this;
        }

        public Criteria andMessageContentIsNull() {
            addCriterion("message_content is null");
            return (Criteria) this;
        }

        public Criteria andMessageContentIsNotNull() {
            addCriterion("message_content is not null");
            return (Criteria) this;
        }

        public Criteria andMessageContentEqualTo(String value) {
            addCriterion("message_content =", value, "messageContent");
            return (Criteria) this;
        }

        public Criteria andMessageContentNotEqualTo(String value) {
            addCriterion("message_content <>", value, "messageContent");
            return (Criteria) this;
        }

        public Criteria andMessageContentGreaterThan(String value) {
            addCriterion("message_content >", value, "messageContent");
            return (Criteria) this;
        }

        public Criteria andMessageContentGreaterThanOrEqualTo(String value) {
            addCriterion("message_content >=", value, "messageContent");
            return (Criteria) this;
        }

        public Criteria andMessageContentLessThan(String value) {
            addCriterion("message_content <", value, "messageContent");
            return (Criteria) this;
        }

        public Criteria andMessageContentLessThanOrEqualTo(String value) {
            addCriterion("message_content <=", value, "messageContent");
            return (Criteria) this;
        }

        public Criteria andMessageContentLike(String value) {
            addCriterion("message_content like", value, "messageContent");
            return (Criteria) this;
        }

        public Criteria andMessageContentNotLike(String value) {
            addCriterion("message_content not like", value, "messageContent");
            return (Criteria) this;
        }

        public Criteria andMessageContentIn(List<String> values) {
            addCriterion("message_content in", values, "messageContent");
            return (Criteria) this;
        }

        public Criteria andMessageContentNotIn(List<String> values) {
            addCriterion("message_content not in", values, "messageContent");
            return (Criteria) this;
        }

        public Criteria andMessageContentBetween(String value1, String value2) {
            addCriterion("message_content between", value1, value2, "messageContent");
            return (Criteria) this;
        }

        public Criteria andMessageContentNotBetween(String value1, String value2) {
            addCriterion("message_content not between", value1, value2, "messageContent");
            return (Criteria) this;
        }

        public Criteria andIsAllIsNull() {
            addCriterion("is_all is null");
            return (Criteria) this;
        }

        public Criteria andIsAllIsNotNull() {
            addCriterion("is_all is not null");
            return (Criteria) this;
        }

        public Criteria andIsAllEqualTo(Integer value) {
            addCriterion("is_all =", value, "isAll");
            return (Criteria) this;
        }

        public Criteria andIsAllNotEqualTo(Integer value) {
            addCriterion("is_all <>", value, "isAll");
            return (Criteria) this;
        }

        public Criteria andIsAllGreaterThan(Integer value) {
            addCriterion("is_all >", value, "isAll");
            return (Criteria) this;
        }

        public Criteria andIsAllGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_all >=", value, "isAll");
            return (Criteria) this;
        }

        public Criteria andIsAllLessThan(Integer value) {
            addCriterion("is_all <", value, "isAll");
            return (Criteria) this;
        }

        public Criteria andIsAllLessThanOrEqualTo(Integer value) {
            addCriterion("is_all <=", value, "isAll");
            return (Criteria) this;
        }

        public Criteria andIsAllIn(List<Integer> values) {
            addCriterion("is_all in", values, "isAll");
            return (Criteria) this;
        }

        public Criteria andIsAllNotIn(List<Integer> values) {
            addCriterion("is_all not in", values, "isAll");
            return (Criteria) this;
        }

        public Criteria andIsAllBetween(Integer value1, Integer value2) {
            addCriterion("is_all between", value1, value2, "isAll");
            return (Criteria) this;
        }

        public Criteria andIsAllNotBetween(Integer value1, Integer value2) {
            addCriterion("is_all not between", value1, value2, "isAll");
            return (Criteria) this;
        }

        public Criteria andMessageAddTimeIsNull() {
            addCriterion("message_add_time is null");
            return (Criteria) this;
        }

        public Criteria andMessageAddTimeIsNotNull() {
            addCriterion("message_add_time is not null");
            return (Criteria) this;
        }

        public Criteria andMessageAddTimeEqualTo(String value) {
            addCriterion("message_add_time =", value, "messageAddTime");
            return (Criteria) this;
        }

        public Criteria andMessageAddTimeNotEqualTo(String value) {
            addCriterion("message_add_time <>", value, "messageAddTime");
            return (Criteria) this;
        }

        public Criteria andMessageAddTimeGreaterThan(String value) {
            addCriterion("message_add_time >", value, "messageAddTime");
            return (Criteria) this;
        }

        public Criteria andMessageAddTimeGreaterThanOrEqualTo(String value) {
            addCriterion("message_add_time >=", value, "messageAddTime");
            return (Criteria) this;
        }

        public Criteria andMessageAddTimeLessThan(String value) {
            addCriterion("message_add_time <", value, "messageAddTime");
            return (Criteria) this;
        }

        public Criteria andMessageAddTimeLessThanOrEqualTo(String value) {
            addCriterion("message_add_time <=", value, "messageAddTime");
            return (Criteria) this;
        }

        public Criteria andMessageAddTimeLike(String value) {
            addCriterion("message_add_time like", value, "messageAddTime");
            return (Criteria) this;
        }

        public Criteria andMessageAddTimeNotLike(String value) {
            addCriterion("message_add_time not like", value, "messageAddTime");
            return (Criteria) this;
        }

        public Criteria andMessageAddTimeIn(List<String> values) {
            addCriterion("message_add_time in", values, "messageAddTime");
            return (Criteria) this;
        }

        public Criteria andMessageAddTimeNotIn(List<String> values) {
            addCriterion("message_add_time not in", values, "messageAddTime");
            return (Criteria) this;
        }

        public Criteria andMessageAddTimeBetween(String value1, String value2) {
            addCriterion("message_add_time between", value1, value2, "messageAddTime");
            return (Criteria) this;
        }

        public Criteria andMessageAddTimeNotBetween(String value1, String value2) {
            addCriterion("message_add_time not between", value1, value2, "messageAddTime");
            return (Criteria) this;
        }

        public Criteria andMessageAddUseridIsNull() {
            addCriterion("message_add_userId is null");
            return (Criteria) this;
        }

        public Criteria andMessageAddUseridIsNotNull() {
            addCriterion("message_add_userId is not null");
            return (Criteria) this;
        }

        public Criteria andMessageAddUseridEqualTo(Integer value) {
            addCriterion("message_add_userId =", value, "messageAddUserid");
            return (Criteria) this;
        }

        public Criteria andMessageAddUseridNotEqualTo(Integer value) {
            addCriterion("message_add_userId <>", value, "messageAddUserid");
            return (Criteria) this;
        }

        public Criteria andMessageAddUseridGreaterThan(Integer value) {
            addCriterion("message_add_userId >", value, "messageAddUserid");
            return (Criteria) this;
        }

        public Criteria andMessageAddUseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("message_add_userId >=", value, "messageAddUserid");
            return (Criteria) this;
        }

        public Criteria andMessageAddUseridLessThan(Integer value) {
            addCriterion("message_add_userId <", value, "messageAddUserid");
            return (Criteria) this;
        }

        public Criteria andMessageAddUseridLessThanOrEqualTo(Integer value) {
            addCriterion("message_add_userId <=", value, "messageAddUserid");
            return (Criteria) this;
        }

        public Criteria andMessageAddUseridIn(List<Integer> values) {
            addCriterion("message_add_userId in", values, "messageAddUserid");
            return (Criteria) this;
        }

        public Criteria andMessageAddUseridNotIn(List<Integer> values) {
            addCriterion("message_add_userId not in", values, "messageAddUserid");
            return (Criteria) this;
        }

        public Criteria andMessageAddUseridBetween(Integer value1, Integer value2) {
            addCriterion("message_add_userId between", value1, value2, "messageAddUserid");
            return (Criteria) this;
        }

        public Criteria andMessageAddUseridNotBetween(Integer value1, Integer value2) {
            addCriterion("message_add_userId not between", value1, value2, "messageAddUserid");
            return (Criteria) this;
        }

        public Criteria andSubscribeUseridIsNull() {
            addCriterion("subscribe_userId is null");
            return (Criteria) this;
        }

        public Criteria andSubscribeUseridIsNotNull() {
            addCriterion("subscribe_userId is not null");
            return (Criteria) this;
        }

        public Criteria andSubscribeUseridEqualTo(String value) {
            addCriterion("subscribe_userId =", value, "subscribeUserid");
            return (Criteria) this;
        }

        public Criteria andSubscribeUseridNotEqualTo(String value) {
            addCriterion("subscribe_userId <>", value, "subscribeUserid");
            return (Criteria) this;
        }

        public Criteria andSubscribeUseridGreaterThan(String value) {
            addCriterion("subscribe_userId >", value, "subscribeUserid");
            return (Criteria) this;
        }

        public Criteria andSubscribeUseridGreaterThanOrEqualTo(String value) {
            addCriterion("subscribe_userId >=", value, "subscribeUserid");
            return (Criteria) this;
        }

        public Criteria andSubscribeUseridLessThan(String value) {
            addCriterion("subscribe_userId <", value, "subscribeUserid");
            return (Criteria) this;
        }

        public Criteria andSubscribeUseridLessThanOrEqualTo(String value) {
            addCriterion("subscribe_userId <=", value, "subscribeUserid");
            return (Criteria) this;
        }

        public Criteria andSubscribeUseridLike(String value) {
            addCriterion("subscribe_userId like", value, "subscribeUserid");
            return (Criteria) this;
        }

        public Criteria andSubscribeUseridNotLike(String value) {
            addCriterion("subscribe_userId not like", value, "subscribeUserid");
            return (Criteria) this;
        }

        public Criteria andSubscribeUseridIn(List<String> values) {
            addCriterion("subscribe_userId in", values, "subscribeUserid");
            return (Criteria) this;
        }

        public Criteria andSubscribeUseridNotIn(List<String> values) {
            addCriterion("subscribe_userId not in", values, "subscribeUserid");
            return (Criteria) this;
        }

        public Criteria andSubscribeUseridBetween(String value1, String value2) {
            addCriterion("subscribe_userId between", value1, value2, "subscribeUserid");
            return (Criteria) this;
        }

        public Criteria andSubscribeUseridNotBetween(String value1, String value2) {
            addCriterion("subscribe_userId not between", value1, value2, "subscribeUserid");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}