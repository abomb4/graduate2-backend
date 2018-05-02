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
     * TODO cache
     *
     * @return tree
     */
    private List<PositionType> getPositionStructure() {
        final List<PositionType> positionTypes = dao.findAll();
        final Map<Long, PositionType> positionTypeMap = positionTypes.stream().collect(Collectors.toMap(PositionType::getId, (t) -> t));

        final LinkedList<PositionType> rootTypes = new LinkedList<>();

        positionTypeMap.forEach((key, value) -> {
            if (value.getParentId() == null) {
                // root node
                rootTypes.add(value);
            } else {

                final PositionType positionType = positionTypeMap.get(value.getParentId());

                if (positionType == null) {
                    LOG.error("Position type {} 's parent id invalid!", positionType);
                    return;
                }
                if (positionType.getSubTypes() == null) {
                    positionType.setSubTypes(new LinkedList<>());
                }
                positionType.getSubTypes().add(value);
            }
        });

        return rootTypes;
    }
}