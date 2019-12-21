package com.newland.airconditionercontroller.utils;

import java.util.List;

public class DataGsonFormat {

    /**
     * ResultObj : {"PageCount":1,"PageIndex":1,"RecordCount":3,"Count":3,"DeviceId":50809,"DataPoints":[{"ApiTag":"temp_value","PointDTO":[{"Value":16,"RecordTime":"2019-10-12 12:36:12"},{"Value":15,"RecordTime":"2019-10-12 12:34:39"},{"Value":1,"RecordTime":"2019-10-12 12:34:33"}]}]}
     * Status : 0
     * StatusCode : 0
     * Msg : null
     * ErrorObj : null
     */

    private ResultObjBean ResultObj;
    private int Status;
    private int StatusCode;
    private Object Msg;
    private Object ErrorObj;

    public ResultObjBean getResultObj() {
        return ResultObj;
    }

    public void setResultObj(ResultObjBean ResultObj) {
        this.ResultObj = ResultObj;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int StatusCode) {
        this.StatusCode = StatusCode;
    }

    public Object getMsg() {
        return Msg;
    }

    public void setMsg(Object Msg) {
        this.Msg = Msg;
    }

    public Object getErrorObj() {
        return ErrorObj;
    }

    public void setErrorObj(Object ErrorObj) {
        this.ErrorObj = ErrorObj;
    }

    public static class ResultObjBean {
        /**
         * PageCount : 1
         * PageIndex : 1
         * RecordCount : 3
         * Count : 3
         * DeviceId : 50809
         * DataPoints : [{"ApiTag":"temp_value","PointDTO":[{"Value":16,"RecordTime":"2019-10-12 12:36:12"},{"Value":15,"RecordTime":"2019-10-12 12:34:39"},{"Value":1,"RecordTime":"2019-10-12 12:34:33"}]}]
         */

        private int PageCount;
        private int PageIndex;
        private int RecordCount;
        private int Count;
        private int DeviceId;
        private List<DataPointsBean> DataPoints;

        public int getPageCount() {
            return PageCount;
        }

        public void setPageCount(int PageCount) {
            this.PageCount = PageCount;
        }

        public int getPageIndex() {
            return PageIndex;
        }

        public void setPageIndex(int PageIndex) {
            this.PageIndex = PageIndex;
        }

        public int getRecordCount() {
            return RecordCount;
        }

        public void setRecordCount(int RecordCount) {
            this.RecordCount = RecordCount;
        }

        public int getCount() {
            return Count;
        }

        public void setCount(int Count) {
            this.Count = Count;
        }

        public int getDeviceId() {
            return DeviceId;
        }

        public void setDeviceId(int DeviceId) {
            this.DeviceId = DeviceId;
        }

        public List<DataPointsBean> getDataPoints() {
            return DataPoints;
        }

        public void setDataPoints(List<DataPointsBean> DataPoints) {
            this.DataPoints = DataPoints;
        }

        public static class DataPointsBean {
            /**
             * ApiTag : temp_value
             * PointDTO : [{"Value":16,"RecordTime":"2019-10-12 12:36:12"},{"Value":15,"RecordTime":"2019-10-12 12:34:39"},{"Value":1,"RecordTime":"2019-10-12 12:34:33"}]
             */

            private String ApiTag;
            private List<PointDTOBean> PointDTO;

            public String getApiTag() {
                return ApiTag;
            }

            public void setApiTag(String ApiTag) {
                this.ApiTag = ApiTag;
            }

            public List<PointDTOBean> getPointDTO() {
                return PointDTO;
            }

            public void setPointDTO(List<PointDTOBean> PointDTO) {
                this.PointDTO = PointDTO;
            }

            public static class PointDTOBean {
                /**
                 * Value : 16
                 * RecordTime : 2019-10-12 12:36:12
                 */

                private int Value;
                private String RecordTime;

                public int getValue() {
                    return Value;
                }

                public void setValue(int Value) {
                    this.Value = Value;
                }

                public String getRecordTime() {
                    return RecordTime;
                }

                public void setRecordTime(String RecordTime) {
                    this.RecordTime = RecordTime;
                }
            }
        }
    }
}
