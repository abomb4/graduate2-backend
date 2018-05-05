package org.wlyyy.itrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wlyyy.itrs.dao.PositionTypeRepository;
import org.wlyyy.itrs.domain.PositionType;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PositionServiceImpl implements PositionService {

    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(PositionServiceImpl.class);

    @Autowired
    private PositionTypeRepository dao;

    @Override
    public List<PositionType> getPositionTypes() {
        return getPositionStructure();
    }

    /**
     * 构造职位类别树
     * TODO cache this
     *
     * @return tree
     */
    private List<PositionType> getPositionStructure() {

        final LinkedList<PositionType> rootTypes = new LinkedList<>();
        final Map<Long, PositionType> positionTypeMap = getLongPositionTypeMap();
        positionTypeMap.forEach((key, value) -> {
            if (value.getParentId() == null) {
                // root node
                rootTypes.add(value);
            }
        });

        return rootTypes;
    }

    /**
     * 构造职位类别map，包含子类信息
     * TODO cache this
     *
     * @return map
     */
    private Map<Long, PositionType> getLongPositionTypeMap() {
        final List<PositionType> positionTypes = dao.findAll();
        final Map<Long, PositionType> positionTypeMap = positionTypes.stream().collect(Collectors.toMap(PositionType::getId, (t) -> t));

        positionTypeMap.forEach((key, value) -> {
            if (value.getParentId() != null) {

                final PositionType positionType = positionTypeMap.get(value.getParentId());

                if (positionType == null) {
                    LOG.error("Position type {} 's parent id invalid!", value);
                    return;
                }
                if (positionType.getSubTypes() == null) {
                    positionType.setSubTypes(new LinkedList<>());
                }
                positionType.getSubTypes().add(value);
            }
        });
        return positionTypeMap;
    }

    @Override
    public PositionType findById(Long id) {
        return getLongPositionTypeMap().get(id);
    }
}
